package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.ForConfirmationSender;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import me.timur.findguideback.service.LanguageService;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.removeKeyboard;
import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 18/06/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GuideBotUniversalUpdateHandler implements GuideBotUpdateHandlerService {

    private final UserService userService;
    private final LanguageService languageService;
    private final ForConfirmationSender forConfirmationSender;
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
            List<String> languages = new ArrayList<>(languageService.getAllNames());

            final ArrayList<BotApiMethod<? extends Serializable>> messages = new ArrayList<>(removeKeyboard(requestDto.getChatId(), requestDto.getPrevMessageId()));
            messages.addAll(sendMessage(
                    requestDto.getChatId(),
                    "Please select the language you use during the excursion",
                    KeyboardUtil.inlineKeyboard(languages, GuideCommand.NEW_GUIDE.command, 2)
            ));

            return messages;
        } else if ((requestDto.getPhotos() != null && !requestDto.getPhotos().isEmpty()) || requestDto.getDocument() != null) {
            log.info("Telegram user {} sending photos", requestDto.getChatId());
            // send a message to the admin bot to confirm the guide
            forConfirmationSender.send(requestDto);
            // send a message to the guide
            return sendMessage(requestDto.getChatId(), "Your certificate has been sent to the admin for confirmation");
        }

        return sendMessage(requestDto.getChatId(), "Something went wrong. Try again");
    }


}
