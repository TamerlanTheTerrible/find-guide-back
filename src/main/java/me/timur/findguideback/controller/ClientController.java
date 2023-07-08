package me.timur.findguideback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Temurbek Ismoilov on 02/05/23.
 */

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping(value = {"", "/"})
    public String client(ModelMap model) {
        model.addAttribute("profile", activeProfile);
        return "client";
    }
}
