package me.timur.findguideback.model.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

@Getter
public class CriteriaSearchResult<T> {
    private final Long count;
    private final List<T> resultList;

    public CriteriaSearchResult(Long count, List<T> resultList) {
        this.count = count;
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "CriteriaSearchResult{" +
                "count=" + count +
                ", resultList=" + Arrays.toString(resultList.toArray()) +
                '}';
    }
}
