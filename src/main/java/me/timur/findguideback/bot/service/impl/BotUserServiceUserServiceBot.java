package me.timur.findguideback.bot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.constant.Command;
import me.timur.findguideback.bot.dto.RequestDto;
import me.timur.findguideback.bot.service.BotApiMethodService;
import me.timur.findguideback.bot.service.BotUpdateHandlerService;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.service.UserService;
import me.timur.findguideback.util.StringUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 04/05/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class BotUserServiceUserServiceBot implements BotUpdateHandlerService {

    private final UserService userService;
    private final BotApiMethodService botApiMethodService;


    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        if (requestDto.getData().equals("/start")) {

        UserCreateDto userCreateDto = UserCreateDto.builder()
                .telegramUsername(requestDto.getUsername())
                .telegramId(requestDto.getChatId())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .phoneNumbers(StringUtil.splitToList(requestDto.getPhone()))
                .build();

            var user = userService.getOrSave(userCreateDto);
            return botApiMethodService.sendMessage(
                    requestDto.getChatId(),
                    "Добро пожаловать" + (user.hasNameOrUsername() ? ", " + user.getFullNameOrUsername() : "")
            );
        }
        return botApiMethodService.sendMessage(
                requestDto.getChatId(),
                "Попробуйте ещё раз");
    }

    @Override
    public Command getType() {
        return Command.USER;
    }
}