package me.timur.findguideback.bot.common.service;

import me.timur.findguideback.bot.client.model.constant.ClientCommand;
import me.timur.findguideback.bot.client.model.dto.RequestDto;
import me.timur.findguideback.bot.common.factory.Type;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public interface BotUpdateHandlerService extends Type<ClientCommand> {
    List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto);
}
