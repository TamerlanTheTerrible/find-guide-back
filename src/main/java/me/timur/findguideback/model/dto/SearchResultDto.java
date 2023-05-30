package me.timur.findguideback.model.dto;

import lombok.Getter;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

@Getter
public class SearchResultDto {
    private final List<GuideDto> resultList;
    private final Long searchId;
    private final Long count;

    public SearchResultDto(List<GuideDto> resultList, Long searchId, Long count) {
        this.resultList = resultList;
        this.searchId = searchId;
        this.count = count;
    }
}
