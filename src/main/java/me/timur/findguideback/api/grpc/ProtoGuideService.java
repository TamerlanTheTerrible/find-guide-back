package me.timur.findguideback.api.grpc;

import com.proto.ProtoBaseResponse;
import com.proto.ProtoGuideCreateDto;
import com.proto.ProtoGuideServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.api.mapper.ProtoMapper;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.service.GuideService;
import org.springframework.stereotype.Component;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoGuideService extends ProtoGuideServiceGrpc.ProtoGuideServiceImplBase{

    private final GuideService guideService;
    private final GrpcRequestHandler requestHandler;

    @Override
    public void save(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideService::save,
                new GuideCreateOrUpdateDto(request),
                ProtoMapper::toProtoGuideDto,
                responseObserver,
                "guide save"
        );
    }

    @Override
    public void update(ProtoGuideCreateDto request, StreamObserver<ProtoBaseResponse> responseObserver) {
        requestHandler.handle(
                guideService::update,
                new GuideCreateOrUpdateDto(request),
                ProtoMapper::toProtoGuideDto,
                responseObserver,
                "guide update"
        );
    }

}
