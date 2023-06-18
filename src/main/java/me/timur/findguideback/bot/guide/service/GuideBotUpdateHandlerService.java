package me.timur.findguideback.bot.guide.service;

import me.timur.findguideback.bot.client.factory.Type;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public interface GuideBotUpdateHandlerService extends Type<GuideCommand> {
    List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto);
}
