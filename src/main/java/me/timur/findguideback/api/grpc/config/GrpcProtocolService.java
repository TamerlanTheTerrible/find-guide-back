package me.timur.findguideback.api.grpc.config;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.api.grpc.ProtoClientService;
import me.timur.findguideback.api.grpc.interceptor.GrpcServerRequestInterceptor;
import me.timur.findguideback.api.grpc.interceptor.GrpcServerResponseInterceptor;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Temurbek Ismoilov on 13/03/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GrpcProtocolService {

    private final ProtoClientService protoClientService;

    @Value("${grpc.server.port}")
    private Integer port;

    private Server server = null;

    /**
     * Start the server serving requests and shutdown resources.
     */
    public Server start() {
        log.info("GRPC Server: start attempt on port {}", port);
        try {
            if (server == null || server.isShutdown()) {
                final ServerBuilder<?> serverBuilder = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create());
                server = serverBuilder
                        .addService(protoClientService)
                        .addService(ServerInterceptors.intercept(
                                protoClientService,
                                new GrpcServerResponseInterceptor(),
                                new GrpcServerRequestInterceptor())
                        )
                        .build();
                server.start();
                log.info("GRPC Server: Started on port {}", port);
            }
        } catch (IOException e) {
            log.error("GRPC Server: Could not start. {}", e.getMessage(), e);
        }
        return server;
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        log.info("GRPC Server: stop attempt");
        if (server != null) {
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                log.info("GRPC Server: stopped");
            } catch (InterruptedException e) {
                log.error("GRPC Server: Failed to stop: {}", e.getMessage(), e);
                throw new FindGuideException(ResponseCode.INTERNAL_ERROR, "GRPC Server: Failed to stop", e.getMessage());
            }
        }
    }

    public boolean isShutdown(){
        if (server != null) {
            return server.isShutdown();
        }
        return true;
    }
}
