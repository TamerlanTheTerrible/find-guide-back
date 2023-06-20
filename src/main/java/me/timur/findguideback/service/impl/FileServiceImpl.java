package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.File;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.FileDto;
import me.timur.findguideback.repository.FileRepository;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.service.FileService;
import org.springframework.stereotype.Service;

/**
 * Created by Temurbek Ismoilov on 20/06/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final GuideRepository guideRepository;

    @Override
    public FileDto save(FileCreateDto createDto) {
        log.info("Creating file: {}", createDto);

        Guide guide = guideRepository.findByUserIdOrUserTelegramId(createDto.getGuideId(), createDto.getGuideTelegramId())
                    .orElseThrow(() -> new RuntimeException(String.format("Could not find guide with id %s or telegram id %s", createDto.getGuideId(), createDto.getGuideTelegramId())));

        var file = fileRepository.save(new File(createDto, guide));

        return new FileDto(file);
    }
}
