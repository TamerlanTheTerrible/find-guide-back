package me.timur.findguideback.controller;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.SearchResultDto;
import me.timur.findguideback.model.response.BaseResponse;
import me.timur.findguideback.service.GuideSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Temurbek Ismoilov on 29/06/23.
 */

@RestController
@RequestMapping("/guide-search")
@RequiredArgsConstructor
public class GuideSearchController {

    private final GuideSearchService guideSearchService;

    @PostMapping(value = {"", "/"})
    public BaseResponse<SearchResultDto> search(@RequestBody GuideFilterDto filterDto) {
        return BaseResponse.of(guideSearchService.getFiltered(filterDto));
    }
}
