package me.timur.findguideback.api.grpc;

import com.google.protobuf.Any;
import com.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.service.GuideService;
import me.timur.findguideback.util.LocalDateTimeUtil;
import me.timur.findguideback.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoGuideService extends ProtoGuideServiceGrpc.ProtoGuideServiceImplBase{

    private final GuideService guideService;

    @Override
    public void save(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        handleRequest(guideService::save, new GuideCreateOrUpdateDto(request), responseObserver, "guide save");
    }

    @Override
    public void update(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        handleRequest(guideService::update, new GuideCreateOrUpdateDto(request), responseObserver, "guide update");
    }

    private <T, R> void handleRequest(Function<T, R> consumer, T param, StreamObserver<ProtoBaseResponse> responseObserver, String methodName) {
        try {
            final R result = consumer.apply(param);
            prepareAndSendResponse((GuideDto) result, responseObserver);
        } catch (FindGuideException e) {
            log.error("Error while {}: {}", methodName, e.getMessage());
            handleException(e.getCode(), e.getMessage(), responseObserver);
        } catch (Exception e) {
            log.error("Unexpected error while {}: {}", methodName, e.getMessage(), e);
            handleException(ResponseCode.INTERNAL_ERROR, e.getMessage(), responseObserver);
        }
    }

    private void prepareAndSendResponse(GuideDto guideDto, StreamObserver<ProtoBaseResponse> responseObserver) {
        var protoResponse = ProtoBaseResponse.newBuilder()
                .setCode(ResponseCode.OK.getCode())
                .setMessage("success")
                .setPayload(
                        Any.pack(toProtoGuideDto(guideDto))
                )
                .build();
        //send response
        responseObserver.onNext(protoResponse);
        responseObserver.onCompleted();
    }

    private ProtoGuideDto toProtoGuideDto(GuideDto guideDto) {
        return ProtoGuideDto.newBuilder()
                .setId(guideDto.getId())
                .setUser(
                        ProtoUserDto.newBuilder()
                                .setId(guideDto.getUser().getId())
                                .setFirstName(guideDto.getUser().getFirstName())
                                .setLastName(guideDto.getUser().getLastName())
                                .setBirthDate(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getBirthDate()))
                                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                                .setPhoneNumbers(StringUtil.join(guideDto.getUser().getPhoneNumbers()))
                                .setTelegramUsername(guideDto.getUser().getTelegramUsername())
                                .setTelegramId(guideDto.getUser().getTelegramId())
                                .setIsActive(guideDto.getUser().getIsActive())
                                .setIsBlocked(guideDto.getUser().getIsBlocked())
                                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                                .build()
                )
                .setLanguageNames(StringUtil.join(guideDto.getLanguageNames()))
                .setRegionNames(StringUtil.join(guideDto.getRegionNames()))
                .setFiles(guideDto.getFiles() == null ? "" : StringUtil.join(guideDto.getFiles().stream().map(f -> String.valueOf(f.getId())).toList()))
                .setDescription(guideDto.getDescription())
                .setIsVerified(guideDto.getIsVerified())
                .setHasCar(guideDto.getHasCar())
                .setIsActive(guideDto.getIsActive())
                .setIsBlocked(guideDto.getIsBlocked())
                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                .build();
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
