package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.File;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.GuideCreateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.repository.*;
import me.timur.findguideback.service.GuideService;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final RegionRepository regionRepository;
    private final FileRepository fileRepository;

    @Override
    public GuideDto save(GuideCreateDto createDto) {
        var languages = languageRepository.findAllByEngNameIn(createDto.getLanguageNames());
        var regions = regionRepository.findAllByEngNameIn(createDto.getRegionNames());
        Set<File> files = null;
        if (createDto.getFiles() != null) {
            files = createDto.getFiles().stream().map(File::new).collect(Collectors.toSet());
        }
        var user = userRepository.findByTelegramId(createDto.getUserTelegramId()).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with telegram id: " + createDto.getUserTelegramId()));
        Guide guide = guideRepository.save(new Guide(createDto, user, languages, regions, files));
        return new GuideDto(guide);
    }

    @Override
    public GuideDto update(GuideCreateDto guideCreateDto) {
//        var user = guideRepository.findByUserIdOrUserTelegramId(createDto.getUserId(), createDto.getUserTelegramId());
        return null;
    }

    @Override
    public GuideDto getById(Long id) {
        return guideRepository.findById(id)
                .map(GuideDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide with id: " + id));
    }

    @Override
    public GuideDto getByTelegramId(Long telegramId) {
        return guideRepository.findById(telegramId)
                .map(GuideDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide with telegram id: " + telegramId));
    }
}
