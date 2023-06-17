package me.timur.findguideback.bot;

import me.timur.findguideback.bot.dto.UserProgress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Configuration
public class BotBeanConfig {

    @Bean
    public ConcurrentHashMap<Long, UserProgress> userProgressMap() {
        return new ConcurrentHashMap<>();
    }
}
