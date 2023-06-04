package me.timur.findguideback.entity;

import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.enums.SearchStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuideSearchTest {

    @Test
    public void testGuideSearchConstructor() {
        User user = new User();
        GuideFilterDto filterDto = new GuideFilterDto();
        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime toDate = LocalDateTime.now().plusDays(1);
        String language = "English";
        String region = "Europe";
        Boolean hasCar = true;
        String comment = "Test comment";
        HashSet<Long> guideIds = new HashSet<>();
        Long totalCount = 5L;

        GuideSearch guideSearch = new GuideSearch(user, filterDto, guideIds, totalCount);
        guideSearch.setFromDate(fromDate);
        guideSearch.setToDate(toDate);
        guideSearch.setLanguage(language);
        guideSearch.setRegion(region);
        guideSearch.setHasCar(hasCar);
        guideSearch.setComment(comment);

        assertNotNull(guideSearch);
        assertEquals(user, guideSearch.getClient());
        assertEquals(fromDate, guideSearch.getFromDate());
        assertEquals(toDate, guideSearch.getToDate());
        assertEquals(language, guideSearch.getLanguage());
        assertEquals(region, guideSearch.getRegion());
        assertEquals(hasCar, guideSearch.getHasCar());
        assertEquals(comment, guideSearch.getComment());
        assertEquals(guideIds.size(), guideSearch.getGuideIds().size());
        assertEquals(totalCount, guideSearch.getSearchCount());
        assertEquals(SearchStatus.CREATED, guideSearch.getStatus());
    }
}