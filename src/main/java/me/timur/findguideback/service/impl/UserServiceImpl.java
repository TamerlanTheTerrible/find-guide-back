package me.timur.findguideback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.repository.UserRepository;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto save(UserCreateDto createDto) {
        User user = new User(createDto);
        userRepository.save(user);
        return new UserDto(user);
    }

    @Override
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "User with id %s not found", id));
    }

    @Override
    public UserDto getByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .map(UserDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "User with telegram id %s not found", telegramId));
    }
}
