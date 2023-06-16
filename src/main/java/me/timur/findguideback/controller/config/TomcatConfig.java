package me.timur.findguideback.controller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Temurbek Ismoilov on 11/03/23.
 */

@Slf4j
@Configuration
public class TomcatConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        log.info("Tomcat started");
        return new TomcatServletWebServerFactory();
    }
}
