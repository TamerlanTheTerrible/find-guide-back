package me.timur.findguideback.bot.service.factory;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.constant.Command;
import me.timur.findguideback.bot.service.BotUpdateHandlerService;
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

    private final EnumMap<Command, BotUpdateHandlerService> map;

    @Autowired
    public CallbackHandlerFactory(List<BotUpdateHandlerService> protocolServices) {
        this.map = new EnumMap<>(Command.class);
        protocolServices.forEach(service -> map.put(service.getType(), service));
    }

    public BotUpdateHandlerService get(Command command) {
        return map.get(command);
    }

    public BotUpdateHandlerService get(String commandStr) {
        return map.get(Command.get(commandStr));
    }
}
