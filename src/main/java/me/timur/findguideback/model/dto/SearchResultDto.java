package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

@Getter
public class SearchResultDto {
    @JsonProperty("result_list")
    private final List<GuideDto> resultList;
    @JsonProperty("search_id")
    private final Long searchId;
    @JsonProperty("count")
    private final Long count;

    public SearchResultDto(List<GuideDto> resultList, Long searchId, Long count) {
        this.resultList = resultList;
        this.searchId = searchId;
        this.count = count;
    }
}
