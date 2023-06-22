package me.timur.findguideback.bot.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.http.HttpHelper;
import me.timur.findguideback.bot.common.http.TelegramResponseDto;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.io.File.separator;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramFileDownloader {

    private final HttpHelper httpHelper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";

    public Path downloadFile(String fileId, String botToken) throws IOException {
        // Construct the Telegram Bot API file URL
        String fileUrl = TELEGRAM_API_BASE_URL + botToken + "/getFile?file_id=" + fileId;
        // Get the file path from the Telegram Bot API
        var fileUrlFromTelegram = getFileUrlFromTelegram(fileUrl, botToken);
//        // Download the file from Telegram Bot API
//        ResponseEntity<String> response = httpHelper.get(fileUrlFromTelegram);
//        // Save the file to the local file system
//        return saveFile(fileId, fileUrlFromTelegram, response.getBody().getBytes());

        Path path = Paths.get("." + separator + "certificate" +  DateTimeFormatter.ofPattern("HH:mm:ss")  + "." + FilenameUtils.getExtension(fileUrlFromTelegram));
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        if (fileUrlFromTelegram != null) {
            try (FileOutputStream fos = new FileOutputStream(path.toString())) {
                new RestTemplate().execute(fileUrlFromTelegram, HttpMethod.GET, null, response -> {
                    response.getBody().transferTo(fos);
                    System.out.println("Image downloaded and saved successfully");
                    return null;
                });
            } catch (IOException e) {
                System.out.println("Error saving image: " + e.getMessage());
                throw new FindGuideException(ResponseCode.TELEGRAM_ERROR, "Error while saving image: %s", e.getMessage());
            }
        } else {
            System.out.println("Image download URL is null");
            throw new FindGuideException(ResponseCode.TELEGRAM_ERROR, "Image download URL is null");
        }

        return path;
    }

    private String getFileUrlFromTelegram(String fileUrl, String botToken){
        final ResponseEntity<String> responseEntity = httpHelper.get(fileUrl);

        try {
            var telegramResponseDTO = objectMapper.readValue(responseEntity.getBody(), TelegramResponseDto.class);
            log.info("Telegram response: {}", telegramResponseDTO);
            Map<String, String> resultMap = (HashMap<String, String>) telegramResponseDTO.getResult();
            return "https://api.telegram.org/file/bot" + botToken + "/" + resultMap.get("file_path");
        } catch (Exception e) {
            log.error("Error while sending request to {}", fileUrl);
            throw new FindGuideException(ResponseCode.TELEGRAM_ERROR, "Error while parsing response of the request %s. Error: ", fileUrl, e.getMessage());
        }
    }

    private Path saveFile(String fileId, String fileUrlFromTelegram, byte[] bytes){
        try {
            final String fileExtension = FilenameUtils.getExtension(fileUrlFromTelegram);
            final String separator = File.separator;

            Path path = Paths.get("." + separator + "certificate" +  DateTimeFormatter.ofPattern("HH:mm:ss")  + "." + fileExtension);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

//            try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
//                fos.write(bytes);
//                System.out.println("Image saved successfully");
//            } catch (IOException e) {
//                System.out.println("Error saving image: " + e.getMessage());
//                throw e;
//            }

            BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(bytes));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos );
            byte [] data = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", path.toFile());

            return path;
        } catch (Exception e) {
            log.error("Error while saving file to the local file system. Error: {}", e.getMessage());
            throw new FindGuideException(ResponseCode.INTERNAL_ERROR, "Error while saving file to the local file system. Error: %s", e.getMessage());
        }
    }

}
