package me.timur.findguideback;

/**
 * Created by Temurbek Ismoilov on 26/05/23.
 */

import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.entity.Region;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.repository.RegionRepository;
import me.timur.findguideback.service.impl.GuideServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GuideServiceTest {
    @Mock
    private GuideRepository guideRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private GuideServiceImpl guideService;

    @Test
    public void testAllFieldsUpdate() {
        // Mock data
        Long userId = 1L;
        Long userTelegramId = 123456L;
        Set<String> languageNames = new HashSet<>(Collections.singletonList("English"));
        Set<String> regionNames = new HashSet<>(Collections.singletonList("New York"));
        Set<FileCreateDto> files = new HashSet<>();
        String description = "Updated description";
        Boolean hasCar = true;

        // Mock GuideCreateOrUpdateDto
        GuideCreateOrUpdateDto requestDto = GuideCreateOrUpdateDto.builder()
                .userId(userId)
                .userTelegramId(userTelegramId)
                .languageNames(languageNames)
                .regionNames(regionNames)
                .files(files)
                .description(description)
                .hasCar(hasCar)
                .build();

        // Mock Guide entity
        Guide guide = new Guide();
        guide.setId(1L);
        guide.setUser(new User());
        guide.setLanguages(new HashSet<>(Collections.singletonList(new Language("Spanish", null, null))));
        guide.setRegions(new HashSet<>(Collections.singletonList(new Region("Washington", null, null))));
        guide.setFiles(new HashSet<>());
        guide.setDescription("Old description");
        guide.setHasCar(false);

        // Mock repositories
        when(guideRepository.findByUserIdOrUserTelegramId(userId, userTelegramId)).thenReturn(Optional.of(guide));
        when(languageRepository.findAllByEngNameIn(languageNames)).thenReturn(new HashSet<>(Collections.singletonList(new Language("English", null, null))));
        when(regionRepository.findAllByEngNameIn(regionNames)).thenReturn(new HashSet<>(Collections.singletonList(new Region("New York", null, null))));
        when(guideRepository.save(guide)).thenReturn(guide);

        // Invoke the method
        GuideDto updatedGuideDto = guideService.update(requestDto);

        // Verify the interactions and assertions
        verify(guideRepository, times(1)).findByUserIdOrUserTelegramId(userId, userTelegramId);
        verify(languageRepository, times(1)).findAllByEngNameIn(languageNames);
        verify(regionRepository, times(1)).findAllByEngNameIn(regionNames);
        verify(guideRepository, times(1)).save(guide);

        assertEquals(requestDto.getDescription(), updatedGuideDto.getDescription());
        assertEquals(requestDto.getHasCar(), updatedGuideDto.getHasCar());
        assertEquals(requestDto.getLanguageNames(), updatedGuideDto.getLanguageNames());
        assertEquals(requestDto.getRegionNames(), updatedGuideDto.getRegionNames());
    }

    @Test
    public void testValidFieldsUpdate() {
        // Mock data
        Long userId = 1L;
        Long userTelegramId = 123456L;
        Set<String> languageNames = new HashSet<>();
        Set<String> regionNames = new HashSet<>();
        Set<FileCreateDto> files = new HashSet<>();
        String description = "";
        Boolean hasCar = null;

        // Mock GuideCreateOrUpdateDto
        GuideCreateOrUpdateDto requestDto = GuideCreateOrUpdateDto.builder()
                .userId(userId)
                .userTelegramId(userTelegramId)
                .languageNames(languageNames)
                .regionNames(regionNames)
                .files(files)
                .description(description)
                .hasCar(hasCar)
                .build();

        // Mock Guide entity
        Guide guide = new Guide();
        guide.setId(1L);
        guide.setUser(new User());
        guide.setLanguages(new HashSet<>(Collections.singletonList(new Language("Spanish", null, null))));
        guide.setRegions(new HashSet<>(Collections.singletonList(new Region("Washington", null, null))));
        guide.setFiles(new HashSet<>());
        guide.setDescription("Old description");
        guide.setHasCar(false);

        // Mock repositories
        when(guideRepository.findByUserIdOrUserTelegramId(userId, userTelegramId)).thenReturn(Optional.of(guide));
//        when(languageRepository.findAllByEngNameIn(languageNames)).thenReturn(new HashSet<>(Collections.singletonList(new Language("English", null, null))));
//        when(regionRepository.findAllByEngNameIn(regionNames)).thenReturn(new HashSet<>(Collections.singletonList(new Region("New York", null, null))));
        when(guideRepository.save(guide)).thenReturn(guide);

        // Invoke the method
        GuideDto updatedGuideDto = guideService.update(requestDto);

        // Verify the interactions and assertions
        verify(guideRepository, times(1)).findByUserIdOrUserTelegramId(userId, userTelegramId);
        verify(languageRepository, times(0)).findAllByEngNameIn(languageNames);
        verify(regionRepository, times(0)).findAllByEngNameIn(regionNames);
        verify(guideRepository, times(1)).save(guide);

        assertEquals("Old description", updatedGuideDto.getDescription());
        assertEquals(false, updatedGuideDto.getHasCar());
        assertEquals(new HashSet<>(Collections.singletonList("Spanish")), updatedGuideDto.getLanguageNames());
        assertEquals(new HashSet<>(Collections.singletonList("Washington")), updatedGuideDto.getRegionNames());
    }
}
