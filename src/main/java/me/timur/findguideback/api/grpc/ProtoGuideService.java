package me.timur.findguideback.api.grpc;

import com.proto.ProtoGuideCreateDto;
import com.proto.ProtoGuideDto;
import com.proto.ProtoGuideServiceGrpc;
import com.proto.ProtoUserDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.model.dto.GuideCreateDto;
import me.timur.findguideback.service.GuideService;
import me.timur.findguideback.util.LocalDateTimeUtil;
import me.timur.findguideback.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoGuideService extends ProtoGuideServiceGrpc.ProtoGuideServiceImplBase{

    private final GuideService guideService;

    @Override
    public void saveGuide(ProtoGuideCreateDto request, StreamObserver<ProtoGuideDto> responseObserver) {
        var guideDto = guideService.save(new GuideCreateDto(request));
        var protoGuideDto = ProtoGuideDto.newBuilder()
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
        responseObserver.onNext(protoGuideDto);
        responseObserver.onCompleted();
    }
}
