package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.GuideCreateDto;
import me.timur.findguideback.model.dto.GuideDto;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface GuideService {
    GuideDto save(GuideCreateDto createDto);
    GuideDto update(GuideCreateDto createDto);
    GuideDto getById(Long id);
    GuideDto getByTelegramId(Long telegramId);
}
