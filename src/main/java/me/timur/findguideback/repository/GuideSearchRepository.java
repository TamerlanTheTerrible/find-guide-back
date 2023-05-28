package me.timur.findguideback.repository;

import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.GuideFilterDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Repository
public interface GuideSearchRepository {
    List<Guide> findAllFiltered(GuideFilterDto filter);
}
