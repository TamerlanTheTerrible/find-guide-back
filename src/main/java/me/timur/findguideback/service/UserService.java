package me.timur.findguideback.service;


import lombok.NonNull;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface UserService {
    UserDto getOrSave(UserCreateDto createDto);
    UserDto savePhone(@NonNull Long telegramId, @NonNull String phone);
    UserDto getById(Long id);
    UserDto getByTelegramId(Long telegramId);
}
