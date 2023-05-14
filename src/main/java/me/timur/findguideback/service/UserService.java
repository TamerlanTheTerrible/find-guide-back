package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface UserService {
    UserDto getOrSave(UserCreateDto createDto);
    UserDto getById(Long id);
    UserDto getByTelegramId(Long telegramId);
}
