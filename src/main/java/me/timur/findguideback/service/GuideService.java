package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface GuideService {
    GuideDto save(GuideCreateOrUpdateDto requestDto);
//    GuideDto getOrCreate(GuideCreateOrUpdateDto requestDto);
    GuideDto addLanguage(Long telegramId, String language);
    GuideDto addRegion(Long telegramId, String region);
    GuideDto update(GuideCreateOrUpdateDto requestDto);
    GuideDto getByTelegramId(Long telegramId);
}
