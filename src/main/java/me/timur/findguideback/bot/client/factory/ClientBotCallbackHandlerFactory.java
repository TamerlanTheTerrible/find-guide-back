package me.timur.findguideback.bot.client.factory;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.client.model.enums.ClientCommand;
import me.timur.findguideback.bot.client.service.ClientBotUpdateHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 13/03/23.
 */

@Slf4j
@Component
public class ClientBotCallbackHandlerFactory {

    private final EnumMap<ClientCommand, ClientBotUpdateHandlerService> map;

    @Autowired
    public ClientBotCallbackHandlerFactory(List<ClientBotUpdateHandlerService> protocolServices) {
        this.map = new EnumMap<>(ClientCommand.class);
        protocolServices.forEach(service -> map.put(service.getType(), service));
    }

    public ClientBotUpdateHandlerService get(ClientCommand clientCommand) {
        return map.get(clientCommand);
    }

    public ClientBotUpdateHandlerService get(String commandStr) {
        return map.containsKey(ClientCommand.get(commandStr))
                ? map.get(ClientCommand.get(commandStr))
                : map.get(ClientCommand.UNIVERSAL);
    }
}
