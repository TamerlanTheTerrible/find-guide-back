package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
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
    private final LanguageRepository languageRepository;
    private final ConcurrentHashMap<Long, NewGuideProgress> newGuideProgressMap;

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
            PhotoSize photo = requestDto.getPhotos().stream()
                    .max(Comparator.comparingInt(PhotoSize::getFileSize))
                    .orElse(null);
            GetFile getFileRequest = new GetFile(photo.getFileId());
        }

        return sendMessage(requestDto.getChatId(), "Something went wrong. Try again");
    }
}
