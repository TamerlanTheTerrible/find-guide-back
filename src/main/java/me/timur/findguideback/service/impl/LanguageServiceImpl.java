package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.service.LanguageService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 19/06/23.
 */

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    @Cacheable("languages")
    public Set<String> getAllNames() {
        return languageRepository.findAll().stream()
                .map(Language::getEngName)
                .collect(Collectors.toSet());
    }
}
