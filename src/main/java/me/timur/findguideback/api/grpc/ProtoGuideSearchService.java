package me.timur.findguideback.api.grpc;

import com.proto.ProtoBaseResponse;
import com.proto.ProtoGuideFilterDto;
import com.proto.ProtoGuideSearchNotificationRequestDto;
import com.proto.ProtoGuideSearchServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.api.grpc.mapper.ProtoMapper;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.service.GuideSearchService;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoGuideSearchService extends ProtoGuideSearchServiceGrpc.ProtoGuideSearchServiceImplBase{

    private final GuideSearchService guideSearchService;
    private final GrpcRequestHandler requestHandler;

    @Override
    public void search(ProtoGuideFilterDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::getFiltered,
                new GuideFilterDto(request),
                ProtoMapper::toProtoGuideDtoList,
                responseObserver,
                "guide filtered search"
        );
    }

    @Override
    public void notify(ProtoGuideSearchNotificationRequestDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::notifyGuides,
                request.getSearchId(),
                responseObserver,
                "guide search notification"
        );
    }
}
