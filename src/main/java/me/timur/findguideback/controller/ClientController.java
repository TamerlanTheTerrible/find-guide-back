package me.timur.findguideback.controller;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Temurbek Ismoilov on 02/05/23.
 */

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String index() {
        return "client";
    }
}
