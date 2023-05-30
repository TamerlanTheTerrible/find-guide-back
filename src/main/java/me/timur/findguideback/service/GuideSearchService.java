package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.SearchResultDto;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

public interface GuideSearchService {
    SearchResultDto getFiltered(GuideFilterDto filterDto);
    void notifyGuides(Long guideSearchId);
}
