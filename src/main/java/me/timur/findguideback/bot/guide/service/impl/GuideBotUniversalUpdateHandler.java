package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.http.HttpHelper;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.service.TelegramFileDownloader;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.FileDto;
import me.timur.findguideback.model.enums.DocumentType;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.service.FileService;
import me.timur.findguideback.service.GuideService;
import me.timur.findguideback.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 18/06/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GuideBotUniversalUpdateHandler implements GuideBotUpdateHandlerService {

    private final UserService userService;
    private final GuideService guideService;
    private final LanguageRepository languageRepository;
    private final FileService fileService;
    private final TelegramFileDownloader telegramFileDownloader;
    private final HttpHelper httpHelper;
    private final ConcurrentHashMap<Long, NewGuideProgress> newGuideProgressMap;

    @Value("${bot.guide.token}")
    private String GUIDE_BOT_TOKEN;
    @Value("${bot.admin.token}")
    private String ADMIN_BOT_TOKEN;

    @Override
    public GuideCommand getType() {
        return GuideCommand.UNIVERSAL;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        if (requestDto.getPhone() != null) {
            //save a phone
            log.info("Telegram user {} saving phone number {}", requestDto.getChatId(), requestDto.getPhone());
            userService.savePhone(requestDto.getChatId(), requestDto.getPhone());

            // add new progress into the map
            log.info("Creating new progress for chatId: {}", requestDto.getChatId());
            newGuideProgressMap.put(requestDto.getChatId(), new NewGuideProgress());

            //send a message with inline keyboard to select a language
            var languages = languageRepository.findAll().stream().map(Language::getEngName).toList();

            return sendMessage(
                    requestDto.getChatId(),
                    "Please select the language you use during the excursion",
                    KeyboardUtil.inlineKeyboard(languages, GuideCommand.NEW_GUIDE.command, 2),
                    requestDto.getPrevMessageId()
            );
        } else if (requestDto.getPhotos() != null && !requestDto.getPhotos().isEmpty()) {
            log.info("Telegram user {} sending photos", requestDto.getChatId());
            // get photo
            var photo = requestDto.getPhotos().stream()
                    .max(Comparator.comparingInt(PhotoSize::getFileSize))
                    .orElseThrow(() -> new FindGuideException(ResponseCode.INVALID_PARAMETERS, "Photo list is empty"));

            // save photo
            var fileDto = fileService.save(FileCreateDto.builder()
                    .fileTelegramId(photo.getFileId())
                    .size(photo.getFileSize().longValue())
                    .guideTelegramId(requestDto.getChatId())
                    .type(DocumentType.CERTIFICATE)
                    .build()
            );

            // send a message to the admin bot to confirm the guide
            sendAdminForConfirmation(fileDto);

        }

        return sendMessage(requestDto.getChatId(), "Something went wrong. Try again");
    }

    private void sendAdminForConfirmation(FileDto fileDto) {
        try {
            var filePath = telegramFileDownloader.downloadFile(fileDto.getFileTelegramId(), GUIDE_BOT_TOKEN);
            var responseEntity = httpHelper.sendResource(
                    "https://api.telegram.org/bot" + ADMIN_BOT_TOKEN + "/sendPhoto" + "?chat_id=" + 3728614,
                    "3728614",
                    new UrlResource(filePath.toUri())
            );
            log.info("Response from admin bot: {}", responseEntity.getBody());
        } catch (Exception e) {
            log.error("Error while sending a photo to the admin bot", e);
        }

    }
}
