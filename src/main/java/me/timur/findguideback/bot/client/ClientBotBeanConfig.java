package me.timur.findguideback.bot.client;

import me.timur.findguideback.bot.client.model.dto.UserProgress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Configuration
public class ClientBotBeanConfig {

    @Bean
    public ConcurrentHashMap<Long, UserProgress> userProgressMap() {
        return new ConcurrentHashMap<>();
    }
}
