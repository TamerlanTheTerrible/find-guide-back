package me.timur.findguideback.api.grpc;

import com.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.api.grpc.mapper.ProtoMapper;
import me.timur.findguideback.model.GetGuideSearchesRequest;
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
    public void notifyGuides(ProtoGuideSearchIdentificationDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::notifyGuides,
                request.getSearchId(),
                responseObserver,
                "guide search notification"
        );
    }

    @Override
    public void close(ProtoGuideSearchIdentificationDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::closeSearch,
                request.getSearchId(),
                responseObserver,
                "guide search closing"
        );
    }

    @Override
    public void get(ProtoGetGuideSearchesRequest request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideSearchService::getGuideSearches,
                new GetGuideSearchesRequest(request),
                ProtoMapper::toProtoGetGuideSearchesResponse,
                responseObserver,
                "get guide searches"
        );
    }
}
