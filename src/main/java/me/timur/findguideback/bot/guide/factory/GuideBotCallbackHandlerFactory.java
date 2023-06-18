package me.timur.findguideback.bot.guide.factory;

import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.bot.guide.model.enums.GuideCommand;
import me.timur.findguideback.bot.guide.service.GuideBotUpdateHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 13/03/23.
 */

@Slf4j
@Component
public class GuideBotCallbackHandlerFactory {

    private final EnumMap<GuideCommand, GuideBotUpdateHandlerService> map;

    @Autowired
    public GuideBotCallbackHandlerFactory(List<GuideBotUpdateHandlerService> protocolServices) {
        this.map = new EnumMap<>(GuideCommand.class);
        protocolServices.forEach(service -> map.put(service.getType(), service));
    }

    public GuideBotUpdateHandlerService get(GuideCommand command) {
        return map.get(command);
    }

    public GuideBotUpdateHandlerService get(String commandStr) {
        return map.get(GuideCommand.get(commandStr));
    }
}
