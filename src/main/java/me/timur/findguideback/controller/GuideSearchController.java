package me.timur.findguideback.controller;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.dto.SearchResultDto;
import me.timur.findguideback.model.response.BaseResponse;
import me.timur.findguideback.service.GuideSearchService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Temurbek Ismoilov on 29/06/23.
 */

@RestController
@RequestMapping("/guide-search")
@CrossOrigin(origins = {"http://localhost:8280", "http://127.0.0.1:8281"})
@RequiredArgsConstructor
public class GuideSearchController {

    private final GuideSearchService guideSearchService;

    @PostMapping(value = {"", "/"})
    public BaseResponse<SearchResultDto> search(@RequestBody GuideFilterDto filterDto) {
        return BaseResponse.of(guideSearchService.getFiltered(filterDto));
    }
}
