package me.timur.findguideback.api.grpc.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Temurbek Ismoilov on 09/03/23.
 */

@Slf4j
@Configuration
public class GrpcConfig {
    @Autowired
    private GrpcProtocolService grpcChannelService;

    @Bean
    public void grpcServer() {
        grpcChannelService.start();
    }

    @PreDestroy
    public void stopGrpcServer() {
        grpcChannelService.stop();
    }
}
