package me.timur.findguideback.api.controller;

import lombok.RequiredArgsConstructor;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.model.response.BaseResponse;
import me.timur.findguideback.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Temurbek Ismoilov on 02/05/23.
 */

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final UserService userService;

    @PostMapping("/")
    public BaseResponse<UserDto> save(@RequestBody UserCreateDto createDto) {
        return BaseResponse.of(userService.save(createDto));
    }

    @GetMapping("/{id}")
    BaseResponse<UserDto> getById(@PathVariable Long id) {
        return BaseResponse.of(userService.getById(id));
    }

    @GetMapping("/telegram-id/{telegramId}")
    BaseResponse<UserDto> getByTelegramId(@PathVariable Long telegramId) {
        return BaseResponse.of(userService.getByTelegramId(telegramId));
    }
}
