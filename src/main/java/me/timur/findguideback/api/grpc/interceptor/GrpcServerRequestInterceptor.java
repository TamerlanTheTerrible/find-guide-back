package me.timur.findguideback.api.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 10/03/23.
 */

@Slf4j
@Component
public class GrpcServerRequestInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {

//        final Map<String, String> mdcContext = buildMdcContext();
//        MDC.setContextMap(mdcContext);

        log.info("GRPC metadata: {}", metadata);

        final ServerCall.Listener<ReqT> original = next.startCall(serverCall, metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(original) {
            @Override
            public void onMessage(final ReqT message) {
//                MDC.setContextMap(mdcContext);
                log.info("GRPC request: {}", message);
                //TODO validation
                super.onMessage(message);
            }

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (FindGuideException e) {
                    handleException(e, serverCall, metadata);
                }
            }

            @Override
            public void onCancel() {
//                MDC.setContextMap(mdcContext);
                super.onCancel();
            }

            @Override
            public void onComplete() {
//                MDC.setContextMap(mdcContext);
                super.onComplete();
            }

            @Override
            public void onReady() {
                try {
                    super.onReady();
                } catch (FindGuideException e) {
                    handleException(e, serverCall, metadata);
                }
            }

            private void handleException(FindGuideException e, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
                log.error("GRPC error while processing method: {} => {}", serverCall.getMethodDescriptor().getFullMethodName(), e.getMessage());

                //TODO Trying to put custom key-pair value in response
                Metadata newMeta = new Metadata();
                newMeta.put(Metadata.Key.of("custom-key", Metadata.ASCII_STRING_MARSHALLER), "custom-value");

                Status status = Status.INTERNAL.withDescription(e.getMessage()).withCause(e);
                serverCall.close(status, newMeta);
            }
        };
    }
}
