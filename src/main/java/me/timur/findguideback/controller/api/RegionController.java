package me.timur.findguideback.controller.api;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.model.response.BaseResponse;
import me.timur.findguideback.service.RegionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 04/07/23.
 */

@RestController
@RequestMapping("/api/region")
@CrossOrigin(origins = {"http://localhost:8280", "http://127.0.0.1:8280"})
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping(value = {"", "/"})
    public BaseResponse<List<String>> getRegionsNames() {
        return BaseResponse.of(new ArrayList<>(regionService.getAllNames()));
    }
}
