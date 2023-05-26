package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.File;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.repository.*;
import me.timur.findguideback.service.GuideService;
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
    public GuideDto save(GuideCreateOrUpdateDto requestDto) {
        var languages = languageRepository.findAllByEngNameIn(requestDto.getLanguageNames());
        var regions = regionRepository.findAllByEngNameIn(requestDto.getRegionNames());
        Set<File> files = null;
        if (requestDto.getFiles() != null) {
            files = requestDto.getFiles().stream().map(File::new).collect(Collectors.toSet());
        }
        var user = userRepository.findByTelegramId(requestDto.getUserTelegramId()).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with telegram id: " + requestDto.getUserTelegramId()));
        Guide guide = guideRepository.save(new Guide(requestDto, user, languages, regions, files));
        return new GuideDto(guide);
    }

    @Override
    public GuideDto update(GuideCreateOrUpdateDto requestDto) {
        var user = guideRepository.findByUserIdOrUserTelegramId(requestDto.getUserId(), requestDto.getUserTelegramId())
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide with user id: " + requestDto.getUserId() + " or telegram id: " + requestDto.getUserTelegramId()));
        if (requestDto.getLanguageNames() != null && !requestDto.getLanguageNames().isEmpty()) {
            user.setLanguages(
                    languageRepository.findAllByEngNameIn(requestDto.getLanguageNames())
            );
        }
        if (requestDto.getRegionNames() != null && !requestDto.getRegionNames().isEmpty()) {
            user.setRegions(
                    regionRepository.findAllByEngNameIn(requestDto.getRegionNames())
            );
        }
        if (requestDto.getFiles() != null && !requestDto.getFiles().isEmpty()) {
            user.setFiles(
                    requestDto.getFiles().stream().map(File::new).collect(Collectors.toSet())
            );
        }
        if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
            user.setDescription(requestDto.getDescription());
        }
        if (requestDto.getHasCar() != null) {
            user.setHasCar(requestDto.getHasCar());
        }

        return new GuideDto(guideRepository.save(user));
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
