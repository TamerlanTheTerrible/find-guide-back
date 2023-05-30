package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.BaseEntity;
import me.timur.findguideback.entity.GuideSearch;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.SearchResultDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.model.enums.SearchStatus;
import me.timur.findguideback.repository.GuideRepository;
import me.timur.findguideback.repository.GuideSearchRepository;
import me.timur.findguideback.repository.UserRepository;
import me.timur.findguideback.service.GuideSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

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
    public SearchResultDto<GuideDto> getFiltered(GuideFilterDto filterDto) {
        log.info("Getting filtered guides: {}", filterDto);
        try {
            //fetch guides and total count from db
            var searchResult = guideRepository.findAllFiltered(filterDto);
            var guides = searchResult.getResultList();
            var totalCount = searchResult.getCount();
            log.info("Filtered guide id list: {}", guides.stream().map(BaseEntity::getId).toList());
            //prepare guide ids and guide dtos
            var guideIds = new HashSet<Long>();
            var guideDtos = new ArrayList<GuideDto>();
            for (var guide : guides) {
                guideIds.add(guide.getId());
                guideDtos.add(new GuideDto(guide));
            }
            var user = getUser(filterDto.getUserId(), filterDto.getTelegramId());
            //save guide search
            var guideSearch = guideSearchRepository.save(new GuideSearch(user, filterDto, guideIds, totalCount));
            log.info("Guide search saved: {}", guideSearch);
            //return result
            return new SearchResultDto<>(totalCount, guideDtos);
        } catch (Exception e) {
            log.error("Error while getting filtered guides: {}", e.getMessage(), e);
            throw new FindGuideException(ResponseCode.INTERNAL_ERROR, "Error while getting filtered guides: %s", e.getMessage());
        }
    }

    @Override
    public void notifyGuides(Long guideSearchId) {
        var guideSearch = guideSearchRepository.findById(guideSearchId)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "Could not find guide search with id: " + guideSearchId));
        Long[] guideIds = guideSearch.getGuideIds().toArray(new Long[0]);
        log.info("Sending notification on search with id {} to guides {}", guideSearchId, guideIds);
        //TODO: send notification to guides
        guideSearch.setStatus(SearchStatus.SEARCHING);
        guideSearchRepository.save(guideSearch);
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