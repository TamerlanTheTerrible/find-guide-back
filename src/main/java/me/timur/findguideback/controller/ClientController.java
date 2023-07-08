package me.timur.findguideback.controller;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * Created by Temurbek Ismoilov on 02/05/23.
 */

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final UserService userService;
    private final Environment environment;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping(value = {"", "/"})
    public String index() {
        System.out.println("Profile by environment: " + Arrays.toString(environment.getActiveProfiles()));
        System.out.println("Profile by value: " + activeProfile);
        return "client";
    }
}
