package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.dto.GuideFilterDto;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface GuideService {
    GuideDto save(GuideCreateOrUpdateDto requestDto);
    GuideDto update(GuideCreateOrUpdateDto requestDto);
    GuideDto getById(Long id);
    GuideDto getByTelegramId(Long telegramId);
    List<GuideDto> getFiltered(GuideFilterDto filterDto);
}
