package me.timur.findguideback.bot.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.client.service.ClientBotUpdateHandlerService;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.common.util.KeyboardUtil;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.service.UserService;
import me.timur.findguideback.util.StringUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

import static me.timur.findguideback.bot.common.util.BotApiMethodUtil.sendMessage;

/**
 * Created by Temurbek Ismoilov on 04/05/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientBotUserService implements ClientBotUpdateHandlerService {

    private final UserService userService;

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        if (requestDto.getData().equals("/start")) {
            log.info("User {} started bot", requestDto.getChatId());
            // Create user
            UserCreateDto userCreateDto = UserCreateDto.builder()
                .telegramUsername(requestDto.getUsername())
                .telegramId(requestDto.getChatId())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .phoneNumbers(StringUtil.splitToList(requestDto.getPhone()))
                .build();

            var user = userService.getOrSave(userCreateDto);
            // Send welcome message with inline keyboard to find a guide
            return sendMessage(
                    requestDto.getChatId(),
                    "Welcome" + (user.hasNameOrUsername() ? ", " + user.getFullNameOrUsername() : ""),
                    KeyboardUtil.requestPhone()
            );
        }

        return sendMessage(
                requestDto.getChatId(),
                "Something went wrong. Try again");
    }

    @Override
    public ClientCommand getType() {
        return ClientCommand.USER;
    }
}
