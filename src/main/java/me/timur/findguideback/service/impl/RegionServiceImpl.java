package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.entity.Region;
import me.timur.findguideback.repository.RegionRepository;
import me.timur.findguideback.service.RegionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 19/06/23.
 */

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    @Cacheable("regions")
    public Set<String> getAllNames() {
        return regionRepository.findAll().stream()
                .map(Region::getEngName)
                .collect(Collectors.toSet());
    }
}
