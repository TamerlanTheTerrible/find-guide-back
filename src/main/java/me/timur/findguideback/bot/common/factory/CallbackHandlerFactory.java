package me.timur.findguideback.bot.common.factory;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.common.service.BotUpdateHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 13/03/23.
 */

@Slf4j
@Component
public class CallbackHandlerFactory {

    private final EnumMap<ClientCommand, BotUpdateHandlerService> map;

    @Autowired
    public CallbackHandlerFactory(List<BotUpdateHandlerService> protocolServices) {
        this.map = new EnumMap<>(ClientCommand.class);
        protocolServices.forEach(service -> map.put(service.getType(), service));
    }

    public BotUpdateHandlerService get(ClientCommand clientCommand) {
        return map.get(clientCommand);
    }

    public BotUpdateHandlerService get(String commandStr) {
        return map.get(ClientCommand.get(commandStr));
    }
}
