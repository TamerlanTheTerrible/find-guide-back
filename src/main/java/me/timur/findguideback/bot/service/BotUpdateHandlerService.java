package me.timur.findguideback.bot.service;

import me.timur.findguideback.bot.constant.Command;
import me.timur.findguideback.bot.dto.RequestDto;
import me.timur.findguideback.bot.service.factory.Type;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public interface BotUpdateHandlerService extends Type<Command> {
    List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto);
}
