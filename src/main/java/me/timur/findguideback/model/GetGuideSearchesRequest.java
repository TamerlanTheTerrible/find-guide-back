package me.timur.findguideback.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.proto.ProtoGetGuideSearchesRequest;
import lombok.Getter;
import me.timur.findguideback.model.enums.SearchStatus;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 31/05/23.
 */

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetGuideSearchesRequest {
    private Long userId;
    private Long telegramId;
    private List<SearchStatus> statuses;

    public GetGuideSearchesRequest(ProtoGetGuideSearchesRequest request) {
        this.userId = request.getUserId();
        this.telegramId = request.getUserTelegramId();
        this.statuses = request.getStatusesList().stream().map(SearchStatus::valueOf).toList();
    }

    @Override
    public String toString() {
        return "GetGuideSearchesRequest{" +
                "userId=" + userId +
                ", telegramId=" + telegramId +
                ", statuses=" + statuses +
                '}';
    }
}
