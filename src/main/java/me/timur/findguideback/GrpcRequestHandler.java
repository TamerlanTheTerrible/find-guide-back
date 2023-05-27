package me.timur.findguideback;

import com.proto.ProtoBaseResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by Temurbek Ismoilov on 27/05/23.
 */

@Slf4j
@Component
public class GrpcRequestHandler {
    public <T, R, U> void handle(Function<T, R> requestMethod, T requestParam, BiConsumer<R, U> responseMethod, StreamObserver<ProtoBaseResponse> responseObserver, String actionName) {
        try {
            final R result = requestMethod.apply(requestParam);
            responseMethod.accept(result, (U) responseObserver);
        } catch (FindGuideException e) {
            log.error("Error while {}: {}", actionName, e.getMessage());
            handleException(e.getCode(), e.getMessage(), responseObserver);
        } catch (Exception e) {
            log.error("Unexpected error while {}: {}", actionName, e.getMessage(), e);
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    private void handleException(ResponseCode code, String message, StreamObserver<ProtoBaseResponse> responseObserver) {
        var protoResponse = ProtoBaseResponse.newBuilder()
                .setCode(code.getCode())
                .setMessage(message == null ? "" : message)
                .build();

        responseObserver.onNext(protoResponse);
        responseObserver.onCompleted();
    }
}
