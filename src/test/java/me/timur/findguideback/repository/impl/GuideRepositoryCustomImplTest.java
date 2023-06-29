package me.timur.findguideback.repository.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.entity.Language;
import me.timur.findguideback.entity.Region;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.model.dto.CriteriaSearchResult;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.repository.LanguageRepository;
import me.timur.findguideback.repository.RegionRepository;
import me.timur.findguideback.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(TestIntegrationConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Slf4j
public class GuideRepositoryCustomImplTest {

    @Autowired
    private GuideRepositoryCustomImpl guideRepositoryCustomImpl;
    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    RegionRepository regionRepository;

    private static final Long TOTAL_GUIDE_NUMBER = 4L;

    @Test
    public void testFindAllFiltered_VerifiedTrue() {
        // given
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(10);
        // when
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        long verifiedGuideNumber = TOTAL_GUIDE_NUMBER-1;
        final List<Guide> guides = result.getResultList();
        assertEquals(verifiedGuideNumber, guides.size());
        assertEquals(verifiedGuideNumber, result.getCount());
        guides.forEach(guide -> assertTrue(guide.getIsVerified() && guide.getIsActive() && !guide.getIsBlocked()));
    }

    @Test
    public void testFindAllFiltered_WithRegionFilter() {
        // given
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(10);
        filter.setRegion("TASHKENT");
        // when
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertTrue(guides.size() > 0);
        guides.forEach(guide -> assertTrue(guide.getRegionNames().contains("TASHKENT")));
    }

    @Test
    public void testFindAllFiltered_WithLanguageFilter() {
        // when
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(10);
        filter.setLanguage("ENGLISH");
        // given
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertTrue(guides.size() > 0);
        guides.forEach(guide -> assertTrue(guide.getLanguageNames().contains("ENGLISH")));
    }

    @Test
    public void testFindAllFiltered_WithLanguageAndRegionFilter() {
        // when
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(10);
        filter.setLanguage("RUSSIAN");
        filter.setRegion("SAMARKAND");
        // given
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertTrue(guides.size() > 0);
        guides.forEach(guide -> assertTrue(guide.getLanguageNames().contains("ENGLISH") && guide.getRegionNames().contains("SAMARKAND")));
    }

    @Test
    public void testFindAllFiltered_WithHasCarFilter() {
        // when
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(10);
        filter.setHasCar(true);
        // given
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertTrue(guides.size() > 0);
        guides.forEach(guide -> assertTrue(guide.getHasCar()));
    }

    @Test
    public void testFindAllFiltered_WithPageSize() {
        // when
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(0);
        filter.setPageSize(1);
        // given
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertEquals(1, guides.size());
    }

    @Test
    public void testFindAllFiltered_WithPageNumber() {
        // when
        GuideFilterDto filter = new GuideFilterDto();
        filter.setPageNumber(1);
        filter.setPageSize(10);
        // given
        CriteriaSearchResult<Guide> result = guideRepositoryCustomImpl.findAllFiltered(filter);
        // then
        List<Guide> guides = result.getResultList();
        assertEquals(0, guides.size());
    }

    @BeforeEach
    private void setup() {
        log.info("-----===== Setting up test data =====-----");

        // languages
        Language english = languageRepository.save(new Language("ENGLISH"));
        Language russian = languageRepository.save(new Language("RUSSIAN"));

        // regions
        Region tashkent = regionRepository.save(new Region("TASHKENT"));
        Region samarkand = regionRepository.save(new Region("SAMARKAND"));

        GuideCreateOrUpdateDto createDto = new GuideCreateOrUpdateDto();
        createDto.setDescription("Test description");
        createDto.setHasCar(true);

        List<User> users = createUsers();
        // guide 1
        Guide guide1 = new Guide(createDto, users.get(0), new HashSet<>(Arrays.asList(english, russian)), new HashSet<>(Arrays.asList(tashkent, samarkand)), null, null);
        guide1.setIsVerified(true);
        // guide 2
        Guide guide2 = new Guide(createDto, users.get(1), new HashSet<>(Collections.singletonList(english)), new HashSet<>(Collections.singletonList(samarkand)), null, null);
        guide2.setIsVerified(true);
        // guide 3
        Guide guide3 = new Guide(createDto, users.get(2), new HashSet<>(Collections.singletonList(russian)), new HashSet<>(Collections.singletonList(tashkent)), null, null);
        guide3.setIsVerified(true);
        // guide 4
        Guide guide4 = new Guide(createDto, users.get(3), new HashSet<>(Arrays.asList(english, russian)), new HashSet<>(Arrays.asList(tashkent, samarkand)), null, null);
        guide4.setIsVerified(false);

        guideRepository.saveAll(Arrays.asList(guide1, guide2, guide3, guide4));
    }

    @NonNull
    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < TOTAL_GUIDE_NUMBER; i++) {
            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setFirstName("John" + i);
            userCreateDto.setLastName("Doe" + i);
            userCreateDto.setBirthDate(LocalDateTime.now());
            userCreateDto.setTelegramId((long) i);
            userCreateDto.setTelegramUsername("johndoe" + i);
            userCreateDto.setPhoneNumbers(Arrays.asList("1234567890", "9876543210"));

            users.add(User.from(userCreateDto));
        }
        userRepository.saveAll(users);
        return users;
    }

    @AfterEach
    private void cleanup() {
        log.info("-----===== Cleaning up test data =====-----");
        guideRepository.deleteAll();
        userRepository.deleteAll();
        languageRepository.deleteAll();
        regionRepository.deleteAll();
    }
}