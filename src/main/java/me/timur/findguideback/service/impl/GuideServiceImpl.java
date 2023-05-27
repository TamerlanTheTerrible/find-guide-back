package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.*;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.repository.*;
import me.timur.findguideback.service.GuideService;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    private final TransportRepository transportRepository;
    @Override
    public GuideDto save(GuideCreateOrUpdateDto requestDto) {
        var languages = getLanguages(requestDto.getLanguageNames());
        var regions = getRegions(requestDto.getRegionNames());
        var transports = getTransports(requestDto.getTransports());
        var files = getFiles(requestDto.getFiles());
        var user = userRepository.findByTelegramId(requestDto.getUserTelegramId()).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with telegram id: " + requestDto.getUserTelegramId()));
        var guide = guideRepository.save(new Guide(requestDto, user, languages, regions, transports, files));
        return new GuideDto(guide);
    }

    @Override
    public GuideDto update(GuideCreateOrUpdateDto requestDto) {
        var user = guideRepository.findByUserIdOrUserTelegramId(requestDto.getUserId(), requestDto.getUserTelegramId())
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide with user id: " + requestDto.getUserId() + " or telegram id: " + requestDto.getUserTelegramId()));
        if (requestDto.getLanguageNames() != null && !requestDto.getLanguageNames().isEmpty()) {
            user.setLanguages(getLanguages(requestDto.getLanguageNames()));
        }
        if (requestDto.getRegionNames() != null && !requestDto.getRegionNames().isEmpty()) {
            user.setRegions(getRegions(requestDto.getRegionNames()));
        }
        if (requestDto.getFiles() != null && !requestDto.getFiles().isEmpty()) {
            user.setFiles(getFiles(requestDto.getFiles()));
        }
        if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
            user.setDescription(requestDto.getDescription());
        }
        if (requestDto.getHasCar() != null) {
            user.setHasCar(requestDto.getHasCar());
        }
        if (requestDto.getTransports() != null && !requestDto.getTransports().isEmpty()) {
            user.setTransports(getTransports(requestDto.getTransports()));
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

    private Set<File> getFiles(Collection<FileCreateDto> args) {
        Set<File> files = null;
        if (args != null) {
            files = args.stream().map(File::new).collect(Collectors.toSet());
        }
        return files;
    }

    private Set<Transport> getTransports(Collection<String> args) {
        Set<Transport> transports = null;
        if (args != null && !args.isEmpty()) {
            transports = transportRepository.findAllByEngNameIn(args);
            if (transports.isEmpty()) {
                throw new FindGuideException(ResponseCode.NOT_FOUND, "Could not find transports with names: " + args);
            }
        }
        return transports;
    }

    private Set<Region> getRegions(Collection<String> args) {
        var regions = regionRepository.findAllByEngNameIn(args);
        if (regions.isEmpty()) {
            throw new FindGuideException(ResponseCode.NOT_FOUND, "Could not find regions with names: " + args);
        }
        return regions;
    }

    private Set<Language> getLanguages(Collection<String> args) {
        var languages = languageRepository.findAllByEngNameIn(args);
        if (languages.isEmpty()) {
            throw new FindGuideException(ResponseCode.NOT_FOUND, "Could not find languages with names: " + args);
        }
        return languages;
    }
}
