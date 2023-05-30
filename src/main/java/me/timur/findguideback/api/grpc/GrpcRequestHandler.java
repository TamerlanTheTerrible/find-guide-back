package me.timur.findguideback.api.grpc;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.proto.ProtoBaseResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Temurbek Ismoilov on 27/05/23.
 */

@Slf4j
@Component
public class GrpcRequestHandler {

    public <T, R, U extends Message> void handle(Function<T, R> requestMethod, T requestParam, Function<R, U> mapperMethod, StreamObserver<ProtoBaseResponse> responseObserver, String actionName) {
        try {
            final R result = requestMethod.apply(requestParam);
            final U protoPayload = mapperMethod.apply(result);
            sendResponse(protoPayload, responseObserver);
        } catch (FindGuideException e) {
            log.error("GRPC error while {}: {}", actionName, e.getMessage());
            handleException(e.getCode(), e.getMessage(), responseObserver);
        } catch (Exception e) {
            log.error("GRPC unexpected error while {}: {}", actionName, e.getMessage(), e);
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    public <T> void handle(Consumer<T> requestMethod, T requestParam, StreamObserver<ProtoBaseResponse> responseObserver, String actionName) {
        try {
            requestMethod.accept(requestParam);
            sendResponse(responseObserver);
        } catch (FindGuideException e) {
            log.error("GRPC error while {}: {}", actionName, e.getMessage());
            handleException(e.getCode(), e.getMessage(), responseObserver);
        } catch (Exception e) {
            log.error("GRPC unexpected error while {}: {}", actionName, e.getMessage(), e);
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    private <U extends Message> void sendResponse(U payload, StreamObserver<ProtoBaseResponse> responseObserver) {
        var protoResponse = ProtoBaseResponse.newBuilder()
                .setCode(ResponseCode.OK.getCode())
                .setMessage("success")
                .setPayload(
                        Any.pack(payload)
                )
                .build();
        //send response
        responseObserver.onNext(protoResponse);
        responseObserver.onCompleted();
    }

    private void sendResponse(StreamObserver<ProtoBaseResponse> responseObserver) {
        var protoResponse = ProtoBaseResponse.newBuilder()
                .setCode(ResponseCode.OK.getCode())
                .setMessage("success")
                .build();
        //send response
        responseObserver.onNext(protoResponse);
        responseObserver.onCompleted();
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