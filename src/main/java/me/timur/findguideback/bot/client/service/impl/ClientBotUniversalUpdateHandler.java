package me.timur.findguideback.bot.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.client.service.ClientBotUpdateHandlerService;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
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
public class ClientBotUniversalUpdateHandler implements ClientBotUpdateHandlerService {

    private final UserService userService;

    @Override
    public ClientCommand getType() {
        return ClientCommand.UNIVERSAL;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        if (requestDto.getPhone() != null) {
            log.info("Telegram user {} saving phone number {}", requestDto.getChatId(), requestDto.getPhone());
            userService.savePhone(requestDto.getChatId(), requestDto.getPhone());
            return sendMessage(
                    requestDto.getChatId(),
                    "Let's search a guide for you",
                    KeyboardUtil.createInlineKeyboard(List.of("Find a guide"), ClientCommand.GUIDE_PARAMS, 2),
                    requestDto.getPrevMessageId()
            );
        }

        return sendMessage(
                requestDto.getChatId(),
                "Something went wrong. Try again");
    }
}
