package me.timur.findguideback.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
}
