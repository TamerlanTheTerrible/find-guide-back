package me.timur.findguideback.bot.guide.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.http.HttpHelper;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.model.enums.CommonCommand;
import me.timur.findguideback.bot.common.service.TelegramFileDownloader;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.FileDto;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Temurbek Ismoilov on 22/06/23.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ForConfirmationSender {

    private final FileService fileService;
    private final TelegramFileDownloader telegramFileDownloader;
    private final HttpHelper httpHelper;

    @Value("${bot.guide.token}")
    private String GUIDE_BOT_TOKEN;
    @Value("${bot.admin.token}")
    private String ADMIN_BOT_TOKEN;

    public void send(RequestDto requestDto) {
        FileDto fileDto;
        if (requestDto.getPhotos() != null && !requestDto.getPhotos().isEmpty()) {
            log.info("Received photo from {}", requestDto.getChatId());
            // get photo
            var photo = requestDto.getPhotos().stream()
                    .max(Comparator.comparingInt(PhotoSize::getFileSize))
                    .orElseThrow(() -> new FindGuideException(ResponseCode.INVALID_PARAMETERS, "Photo list is empty"));
            // save photo
            fileDto = getFileDto(requestDto, photo.getFileId(), Long.valueOf(photo.getFileSize()), "jpg");
        } else {
            log.info("Received document from {}", requestDto.getChatId());

            // save photo
            var document = requestDto.getDocument();
            fileDto = getFileDto(requestDto, document.getFileId(), document.getFileSize(), document.getFileName().split("\\.")[1]);
        }

        send(fileDto);
    }

    private FileDto getFileDto(RequestDto requestDto, String fileId, Long fileSize, String extension) {
        return fileService.save(FileCreateDto.builder()
                .fileTelegramId(fileId)
                .size(fileSize)
                .guideTelegramId(requestDto.getChatId())
                .type(DocumentType.CERTIFICATE)
                .extension(DocumentExtension.valueOf(extension.toUpperCase()))
                .build()
        );
    }

    private void send(FileDto fileDto) {
        try {
            // download a photo
            var filePath = telegramFileDownloader.downloadFile(fileDto.getFileTelegramId(), GUIDE_BOT_TOKEN);

            // prepare a url
            var adminChatId = 3728614L;
            var extension = FilenameUtils.getExtension(filePath.toString());
            var url = "https://api.telegram.org/bot" + ADMIN_BOT_TOKEN + (extension.equals("jpg") ? "/sendPhoto" : "/sendDocument") + "?chat_id=" + adminChatId;
            // prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            // prepare body
            var guideTgId = fileDto.getGuide().getUser().getTelegramId();
            var keyboardJson = "{\n" +
                    "    \"inline_keyboard\": [\n" +
                    "        [\n" +
                    "            {\n" +
                    "                \"text\": \"confirm\",\n" +
                    "                \"callback_data\": \"" + CommonCommand.CONFIRM.command + "-" + guideTgId + "\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"text\": \"reject\",\n" +
                    "                \"callback_data\": \"" + CommonCommand.REJECT.command + "-" + guideTgId + "\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    ]\n" +
                    "}";

            var guide = fileDto.getGuide();
            var languages = Arrays.toString(guide.getLanguageNames().toArray());
            var regions = Arrays.toString(guide.getRegionNames().toArray());
            var caption = "name: " + guide.getUser().getFullNameOrUsername() + "\n" +
                    "username: " + (guide.getUser().getTelegramUsername() != null ? "@" + guide.getUser().getTelegramUsername() : "") + "\n" +
                    "phone: " + guide.getUser().getPhoneNumbers().get(0) + "\n" +
                    "language: " + languages.substring(1, languages.length()-1) + "\n" +
                    "region: " + regions.substring(1, regions.length()-1);

            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("chat_id", adminChatId);
            requestBody.add("photo", new FileSystemResource(new java.io.File(filePath.toUri())));
            requestBody.add("caption", caption);
            requestBody.add("reply_markup", keyboardJson);
            // send a photo
            httpHelper.post(url, requestBody, headers);

            // delete a photo after sending
            Files.deleteIfExists(filePath);

            log.info("Photo successfully sent to the admin bot");
        } catch (Exception e) {
            log.error("Error while sending a photo to the admin bot", e);
        }
    }
}
