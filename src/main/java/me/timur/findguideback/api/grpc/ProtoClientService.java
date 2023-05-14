package me.timur.findguideback.api.grpc;

import com.google.protobuf.Any;
import com.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.service.UserService;
import me.timur.findguideback.util.LocalDateTimeUtil;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 03/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoClientService extends ProtoClientServiceGrpc.ProtoClientServiceImplBase {

    private final UserService userService;

    @Override
    public void getOrSave(ProtoUserCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        try {
            //save user
            var userDto = userService.getOrSave(UserCreateDto.create(request));
            //send response
            sendResponse(userDto, responseObserver);
        } catch (Exception e) {
            log.error("Error while saving user: {}", e.getMessage(), e);
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    @Override
    public void getById(ProtoGetUserByIdRequest request, StreamObserver<ProtoBaseResponse> responseObserver) {
        try {
            //get user
            var userDto = userService.getById(request.getId());
            //send response
            sendResponse(userDto, responseObserver);
        } catch (Exception e) {
            log.error("Error while getting user: {}", e.getMessage());
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    @Override
    public void getByTelegramId(ProtoGetUserByTelegramIdRequest request, StreamObserver<ProtoBaseResponse> responseObserver) {
        try {
            //get user
            var userDto = userService.getByTelegramId(request.getTelegramId());
            //send response
            sendResponse(userDto, responseObserver);
        } catch (Exception e) {
            log.error("Error while getting user: {}", e.getMessage());
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    private void sendResponse(UserDto userDto, StreamObserver<ProtoBaseResponse> responseObserver) {
        //build payload
        var payload = ProtoUserDto.newBuilder()
                .setId(userDto.getId())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setTelegramUsername(userDto.getTelegramUsername())
                .setTelegramId(userDto.getTelegramId())
                .setPhoneNumbers(userDto.getPhoneNumbers() == null ? "" : String.join(",", userDto.getPhoneNumbers()))
                .setIsActive(userDto.getIsActive())
                .setIsBlocked(userDto.getIsBlocked())
                .setDateCreated(LocalDateTimeUtil.toString(userDto.getDateCreated()))
                .setDateUpdated(LocalDateTimeUtil.toString(userDto.getDateUpdated()))
                .build();

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

    private void handleException(ResponseCode code, String message, StreamObserver<ProtoBaseResponse> responseObserver) {
        var protoResponse = ProtoBaseResponse.newBuilder()
                .setCode(code.getCode())
                .setMessage(message == null ? "" : message)
                .build();

        responseObserver.onNext(protoResponse);
        responseObserver.onCompleted();
    }
}
