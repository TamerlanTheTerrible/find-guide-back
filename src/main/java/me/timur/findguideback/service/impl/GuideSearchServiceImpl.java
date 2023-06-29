package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.BaseEntity;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.entity.GuideSearch;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.GetGuideSearchesRequest;
import me.timur.findguideback.model.dto.*;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.model.enums.SearchStatus;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.repository.GuideSearchRepository;
import me.timur.findguideback.repository.UserRepository;
import me.timur.findguideback.service.GuideSearchService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GuideSearchServiceImpl implements GuideSearchService {

    private final GuideSearchRepository guideSearchRepository;
    private final GuideRepository guideRepository;
    private final UserRepository userRepository;

    @Override
    public SearchResultDto getFiltered(GuideFilterDto filterDto) {
        log.info("Getting filtered guides: {}", filterDto);
        try {
            //fetch guides and total count from db
            CriteriaSearchResult<Guide> searchResult = guideRepository.findAllFiltered(filterDto);
            List<Guide> guides = searchResult.getResultList();
            Long totalCount = searchResult.getCount();
            log.info("Filtered guide id list: {}", guides.stream().map(BaseEntity::getId).collect(Collectors.toList()));
            //prepare guide ids and guide dtos
            Set<Long> guideIds = new HashSet<>();
            List<GuideDto> guideDtos = new ArrayList<>();
            for (Guide guide : guides) {
                guideIds.add(guide.getId());
                guideDtos.add(new GuideDto(guide));
            }
            User user = getUser(filterDto.getUserId(), filterDto.getTelegramId());
            //save guide search
            GuideSearch guideSearch = guideSearchRepository.save(new GuideSearch(user, filterDto, guideIds, totalCount));
            log.info("Guide search saved: {}", guideSearch);
            //return result
            return new SearchResultDto(guideDtos, guideSearch.getId(), totalCount);
        } catch (Exception e) {
            log.error("Error while getting filtered guides: {}", e.getMessage(), e);
            throw new FindGuideException(ResponseCode.INTERNAL_ERROR, "Error while getting filtered guides: %s", e.getMessage());
        }
    }

    @Override
    public void notifyGuides(Long guideSearchId) {
        GuideSearch guideSearch = getGuideSearch(guideSearchId);
        Long[] guideIds = guideSearch.getGuideIds().toArray(new Long[0]);
        log.info("Sending notification on search with id {} to guides {}", guideSearchId, guideIds);
        //TODO: send notification to guides
        guideSearch.setStatus(SearchStatus.SEARCHING);
        guideSearchRepository.save(guideSearch);
    }

    @Override
    public boolean closeSearch(Long guideSearchId) {
        GuideSearch guideSearch = getGuideSearch(guideSearchId);
        guideSearch.setStatus(SearchStatus.CLOSED);
        guideSearchRepository.save(guideSearch);
        log.info("Close guide search with id {}", guideSearchId);
        return true;
    }

    @Override
    public List<GuideSearchDto> getGuideSearches(GetGuideSearchesRequest request) {
        log.info("Getting guide searches: {}", request);
        List<GuideSearch> guideSearches = guideSearchRepository.findAllByClientAndStatusIn(
                getUser(request.getUserId(), request.getTelegramId()),
                request.getStatuses()
        );
        log.info("Found guide searches: {}", Arrays.toString(guideSearches.stream().map(BaseEntity::getId).toArray()));

        return guideSearches.stream().map(GuideSearchDto::new).collect(Collectors.toList());
    }

    private GuideSearch getGuideSearch(Long guideSearchId) {
        return guideSearchRepository.findById(guideSearchId)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide search with id: " + guideSearchId));
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

}