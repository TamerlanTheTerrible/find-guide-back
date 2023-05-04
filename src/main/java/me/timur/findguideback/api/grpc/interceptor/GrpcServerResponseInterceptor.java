package me.timur.findguideback.api.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Temurbek Ismoilov on 10/03/23.
 */

@Slf4j
public class GrpcServerResponseInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {

        return next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
                    @Override
                    public void sendMessage(RespT message) {
                        log.info("GRPC response: " + message);
                        super.sendMessage(message);
                    }
                },
                metadata);
    }
}
