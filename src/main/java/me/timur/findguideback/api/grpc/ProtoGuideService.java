package me.timur.findguideback.api.grpc;

import com.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.SearchResultDto;
import me.timur.findguideback.service.GuideSearchService;
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
    private final GuideSearchService guideSearchService;
    private final GrpcRequestHandler requestHandler;

    @Override
    public void save(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideService::save,
                new GuideCreateOrUpdateDto(request),
                this::toProtoGuideDto,
                responseObserver,
                "guide save"
        );
    }

    @Override
    public void update(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideService::update,
                new GuideCreateOrUpdateDto(request),
                this::toProtoGuideDto,
                responseObserver,
                "guide update"
        );
    }

    @Override
    public void search(ProtoGuideFilterDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::getFiltered,
                new GuideFilterDto(request),
                this::toProtoGuideDtoList,
                responseObserver,
                "guide filtered search"
        );
    }

    private ProtoGuideDtoList toProtoGuideDtoList(SearchResultDto<GuideDto> result) {
        return ProtoGuideDtoList.newBuilder()
                .addAllItems(
                        result.getResultList().stream().map(this::toProtoGuideDto).toList()
                )
                .setTotalCount(result.getCount())
                .build();
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

}
