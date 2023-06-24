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
        log.info("Creating guide: {}", requestDto);
        var languages = getLanguages(requestDto.getLanguageNames());
        var regions = getRegions(requestDto.getRegionNames());
        var transports = getTransports(requestDto.getTransports());
        var files = getFiles(requestDto.getFiles());
        var user = getUser(requestDto.getUserId(), requestDto.getUserTelegramId());

        // check if the user has already created a guide
        guideRepository.findByUserTelegramId(requestDto.getUserTelegramId())
                .ifPresent(oldGuide -> {
                    log.info("User {} is already a guide Deleting the old guide record before creating new one", oldGuide);
                    guideRepository.delete(oldGuide);
                });

        var guide = guideRepository.save(new Guide(requestDto, user, languages, regions, transports, files));
        return new GuideDto(guide);
    }

    @Override
    public GuideDto addLanguage(Long telegramId, String language) {
        var guide = getEntityByTelegramId(telegramId);
        guide.addLanguage(languageRepository
                .findByEngName(language)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find language with name: " + language))
        );
        return new GuideDto(guideRepository.save(guide));
    }

    @Override
    public GuideDto addRegion(Long telegramId, String region) {
        var guide = getEntityByTelegramId(telegramId);
        guide.addRegion(regionRepository
                .findByEngName(region)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find region with name: " + region))
        );
        return new GuideDto(guideRepository.save(guide));
    }

    private User getUser(Long userId, Long userTelegramId) {
        if (userId != null && userId > 0) {
            return userRepository.findById(userId).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with id: " + userId));
        } else if (userTelegramId != null && userTelegramId > 0) {
            return userRepository.findByTelegramId(userTelegramId).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with telegram id: " + userTelegramId));
        } else {
            throw new FindGuideException(ResponseCode.INVALID_PARAMETERS, "User id or user telegram id must be provided");
        }
    }

    private User getUser(GuideCreateOrUpdateDto requestDto) {
        return userRepository.findByTelegramId(requestDto.getUserTelegramId()).orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find user with telegram id: " + requestDto.getUserTelegramId()));
    }

    @Override
    public GuideDto update(GuideCreateOrUpdateDto requestDto) {
        log.info("Updating guide: {}", requestDto);

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
    public GuideDto getByTelegramId(Long telegramId) {
        var guideOptional = guideRepository.findByUserTelegramId(telegramId);
        return guideOptional.map(GuideDto::new).orElse(null);
    }

    @Override
    public GuideDto confirmGuide(Long telegramId, boolean isConfirmed) {
        log.info("Confirming/rejecting guide with telegram id: {} isConfirmed: {}", telegramId, isConfirmed);
        var guide = getEntityByTelegramId(telegramId);
        guide.setIsVerified(isConfirmed);
        return new GuideDto(guideRepository.save(guide));
    }

    private Guide getEntityByTelegramId(Long telegramId) {
        return guideRepository.findByUserTelegramId(telegramId)
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
