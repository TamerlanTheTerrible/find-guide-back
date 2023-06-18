package me.timur.findguideback.bot.client.service;

import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.common.model.dto.RequestDto;
import me.timur.findguideback.bot.client.factory.Type;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 14/04/23.
 */

public interface ClientBotUpdateHandlerService extends Type<ClientCommand> {
    List<BotApiMethod<? extends Serializable>> handle(RequestDto requestDto);
}
