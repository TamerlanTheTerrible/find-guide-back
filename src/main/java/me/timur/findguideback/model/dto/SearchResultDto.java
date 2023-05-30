package me.timur.findguideback.model.dto;

import lombok.Getter;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

@Getter
public class SearchResultDto<T> {
    private final Long count;
    private final List<T> resultList;

    public SearchResultDto(Long count, List<T> resultList) {
        this.count = count;
        this.resultList = resultList;
    }
}
