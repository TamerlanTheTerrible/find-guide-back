package me.timur.findguideback.bot.guide.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.client.service.impl.ClientBotUserService;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
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
public class GuideBotUserService implements GuideBotUpdateHandlerService {

    private final ClientBotUserService userService;

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto) {
        return userService.handle(requestDto);
    }

    @Override
    public GuideCommand getType() {
        return GuideCommand.USER;
    }
}
