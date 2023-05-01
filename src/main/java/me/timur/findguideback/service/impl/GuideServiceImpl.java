package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.File;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.GuideCreateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.repository.FileRepository;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.repository.RegionRepository;
import me.timur.findguideback.service.GuideService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;
    private final LanguageRepository languageRepository;
    private final RegionRepository regionRepository;
    private final FileRepository fileRepository;

    @Override
    public GuideDto save(GuideCreateDto createDto) {
        var languages = languageRepository.findAllByEngNameIn(createDto.getLanguageNames());
        var regions = regionRepository.findAllByEngNameIn(createDto.getRegionNames());
        var files = createDto.getFiles().stream().map(File::new).collect(Collectors.toSet());
        Guide guide = new Guide(createDto, languages, regions, files);
        guide = guideRepository.save(guide);
        return new GuideDto(guide);
    }

    @Override
    public GuideDto getById(Long id) {
        return null;
    }

    @Override
    public GuideDto getByTelegramId(Long telegramId) {
        return null;
    }
}
