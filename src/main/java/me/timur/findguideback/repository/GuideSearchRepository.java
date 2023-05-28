package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.GuideFilterDto;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

public interface GuideSearchRepository {
    List<Guide> findAllFiltered(GuideFilterDto filter);
}
