package me.timur.findguideback.api.grpc;

import com.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.api.mapper.ProtoMapper;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 03/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoClientService extends ProtoClientServiceGrpc.ProtoClientServiceImplBase {

    private final UserService userService;
    private final GrpcRequestHandler requestHandler;

    @Override
    public void getOrSave(ProtoUserCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                userService::getOrSave,
                UserCreateDto.create(request),
                ProtoMapper::toProtoUserDto,
                responseObserver,
                "getting or saving user"
        );
    }

    @Override
    public void getById(ProtoGetUserByIdRequest request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                userService::getById,
                request.getId(),
                ProtoMapper::toProtoUserDto,
                responseObserver,
                "getting user by id"
        );
    }

    @Override
    public void getByTelegramId(ProtoGetUserByTelegramIdRequest request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                userService::getByTelegramId,
                request.getTelegramId(),
                ProtoMapper::toProtoUserDto,
                responseObserver,
                    "getting user by telegram id"
        );
    }

}
