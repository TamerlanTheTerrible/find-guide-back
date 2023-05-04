package me.timur.findguideback;

import me.timur.findguideback.api.controller.TomcatConfig;
import me.timur.findguideback.api.grpc.config.GrpcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@Import({TomcatConfig.class, GrpcConfig.class})
@EnableScheduling
public class FindGuideBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindGuideBackApplication.class, args);
    }

}
