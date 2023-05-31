package me.timur.findguideback.service;

import me.timur.findguideback.model.GetGuideSearchesRequest;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.GuideSearchDto;
import me.timur.findguideback.model.dto.SearchResultDto;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

public interface GuideSearchService {
    SearchResultDto getFiltered(GuideFilterDto filterDto);
    void notifyGuides(Long guideSearchId);
    boolean closeSearch(Long guideSearchId);
    List<GuideSearchDto> getGuideSearches(GetGuideSearchesRequest request);
}
