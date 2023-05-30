package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.CriteriaSearchResult;
import me.timur.findguideback.model.dto.GuideFilterDto;
import org.springframework.stereotype.Repository;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Repository
public interface GuideRepositoryCustom {
    CriteriaSearchResult<Guide> findAllFiltered(GuideFilterDto filter);
}
