package me.timur.findguideback.bot.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.http.TelegramResponseDto;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.io.File.separator;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramFileDownloader {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";

    public Path downloadFile(String fileId, String botToken) throws IOException {
        // Construct the Telegram Bot API file URL
        String fileUrl = TELEGRAM_API_BASE_URL + botToken + "/getFile?file_id=" + fileId;
        // Get the file path from the Telegram Bot API
        var fileUrlFromTelegram = getFileUrlFromTelegram(fileUrl, botToken);
        // Save the file to the local file system
        return saveFile(fileUrlFromTelegram, fileId);
    }

    private String getFileUrlFromTelegram(String fileUrl, String botToken){
        try {
            final ResponseEntity<String> responseEntity = restTemplate.getForEntity(fileUrl, String.class);
            var telegramResponseDTO = objectMapper.readValue(responseEntity.getBody(), TelegramResponseDto.class);
            log.info("Telegram response: {}", telegramResponseDTO);
            Map<String, String> resultMap = (HashMap<String, String>) telegramResponseDTO.getResult();
            return "https://api.telegram.org/file/bot" + botToken + "/" + resultMap.get("file_path");
        } catch (Exception e) {
            log.error("Error while sending request to {}", fileUrl);
            throw new FindGuideException(ResponseCode.TELEGRAM_ERROR, "Error while parsing response of the request %s. Error: ", fileUrl, e.getMessage());
        }
    }

    private Path saveFile(String fileUrlFromTelegram, String fileId) throws IOException {
        Path path = Paths.get("." + separator + "certificate-" + fileId + "." + FilenameUtils.getExtension(fileUrlFromTelegram));
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (FileOutputStream fos = new FileOutputStream(path.toString())) {
            restTemplate.execute(fileUrlFromTelegram, HttpMethod.GET, null, response -> {
                response.getBody().transferTo(fos);
                System.out.println("Image downloaded and saved successfully");
                return null;
            });
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
            throw new FindGuideException(ResponseCode.TELEGRAM_ERROR, "Error while saving image: %s", e.getMessage());
        }
        return path;
    }
}
