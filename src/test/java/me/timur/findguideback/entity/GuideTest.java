package me.timur.findguideback.entity;

import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GuideTest {

    @Test
    public void testGuideConstructor() {
        GuideCreateOrUpdateDto createDto = new GuideCreateOrUpdateDto();
        createDto.setDescription("Test description");
        createDto.setHasCar(true);

        User user = new User();
        Set<Language> languages = new HashSet<>();
        Set<Region> regions = new HashSet<>();
        Set<Transport> transports = new HashSet<>();
        Set<File> files = new HashSet<>();

        Guide guide = new Guide(createDto, user, languages, regions, transports, files);

        assertEquals(user, guide.getUser());
        assertEquals(languages, guide.getLanguages());
        assertEquals(regions, guide.getRegions());
        assertEquals(transports, guide.getTransports());
        assertEquals(files, guide.getFiles());
        assertEquals("Test description", guide.getDescription());
        assertFalse(guide.getIsVerified());
        assertTrue(guide.getHasCar());
        assertTrue(guide.getIsActive());
        assertFalse(guide.getIsBlocked());
    }

    @Test
    public void testGetLanguageNames() {
        Language language1 = new Language("English", "English", "English");
        Language language2 = new Language("Russian", "Russian", "Russian");
        Set<Language> languages = new HashSet<>();
        languages.add(language1);
        languages.add(language2);

        Guide guide = new Guide();
        guide.setLanguages(languages);

        Set<String> languageNames = guide.getLanguageNames();

        assertTrue(languageNames.contains("English"));
        assertTrue(languageNames.contains("Russian"));
        assertEquals(2, languageNames.size());
    }

    @Test
    public void testGetRegionNames() {
        Region region1 = new Region("Tashkent", "Ташкент", "Toshkent");
        Region region2 = new Region("Samarkand", "Самарканд", "Samarqand");
        Set<Region> regions = new HashSet<>();
        regions.add(region1);
        regions.add(region2);

        Guide guide = new Guide();
        guide.setRegions(regions);

        Set<String> regionNames = guide.getRegionNames();

        assertTrue(regionNames.contains("Tashkent"));
        assertTrue(regionNames.contains("Samarkand"));
        assertEquals(2, regionNames.size());
    }

    @Test
    public void testGetLanguageNamesWithEmptyLanguages() {
        Guide guide = new Guide();

        Set<String> languageNames = guide.getLanguageNames();

        assertNull(languageNames);
    }

    @Test
    public void testGetLanguageNamesWithNullLanguages() {
        Guide guide = new Guide();
        guide.setLanguages(null);

        Set<String> languageNames = guide.getLanguageNames();

        assertNull(languageNames);
    }

    @Test
    public void testGetRegionNamesWithEmptyRegions() {
        Guide guide = new Guide();

        Set<String> regionNames = guide.getRegionNames();

        assertNull(regionNames);
    }

    @Test
    public void testGetRegionNamesWithNullRegions() {
        Guide guide = new Guide();
        guide.setRegions(null);

        Set<String> regionNames = guide.getRegionNames();

        assertNull(regionNames);
    }
}
