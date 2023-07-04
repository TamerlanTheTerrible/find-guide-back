package me.timur.findguideback.controller.api;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.model.response.BaseResponse;
import me.timur.findguideback.service.LanguageService;
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
@RequestMapping("/api/language")
@CrossOrigin(origins = {"http://localhost:8280", "http://127.0.0.1:8280"})
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping(value = {"", "/"})
    public BaseResponse<List<String>> getLanguagesNames() {
        return BaseResponse.of(new ArrayList<>(languageService.getAllNames()));
    }

}
