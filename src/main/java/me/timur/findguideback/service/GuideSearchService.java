package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.GuideDto;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

public interface GuideSearchService {
    List<GuideDto> search(String query);
}
