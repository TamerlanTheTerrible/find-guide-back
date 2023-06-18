package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 18/06/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GuideBotUniversalUpdateHandler implements GuideBotUpdateHandlerService {

    private final UserService userService;

    @Override
    public GuideCommand getType() {
        return GuideCommand.UNIVERSAL;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        if (requestDto.getPhone() != null) {
            log.info("Telegram user {} saving phone number {}", requestDto.getChatId(), requestDto.getPhone());
            userService.savePhone(requestDto.getChatId(), requestDto.getPhone());
            return sendMessage(
                    requestDto.getChatId(),
                    "Some message"
//                    KeyboardUtil.createInlineKeyboard(List.of("some command"), ClientCommand.GUIDE_PARAMS, 2),
//                    requestDto.getPrevMessageId()
            );
        }

        return sendMessage(
                requestDto.getChatId(),
                "Something went wrong. Try again");
    }
}
