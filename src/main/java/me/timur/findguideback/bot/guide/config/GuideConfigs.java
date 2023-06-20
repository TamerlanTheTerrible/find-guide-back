package me.timur.findguideback.bot.guide.config;

import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Temurbek Ismoilov on 19/06/23.
 */

@Configuration
public class GuideConfigs {

    @Bean
    public ConcurrentHashMap<Long, NewGuideProgress> newGuideProgressMap() {
        return new ConcurrentHashMap<>();
    }
}
