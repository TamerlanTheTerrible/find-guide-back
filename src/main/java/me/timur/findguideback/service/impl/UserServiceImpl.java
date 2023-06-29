package me.timur.findguideback.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.dto.UserCreateDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.model.enums.ResponseCode;
import me.timur.findguideback.repository.UserRepository;
import me.timur.findguideback.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getOrSave(UserCreateDto createDto) {
        // get user from repository by telegram id, if user is present, update user, else save user
        User user = userRepository.findByTelegramId(createDto.getTelegramId())
                .map(userFromDB -> update(userFromDB, createDto))
                .orElseGet(() -> save(createDto));

        log.info("User got or saved {}", user);
        return new UserDto(user);
    }

    @Override
    public UserDto savePhone(@NonNull Long telegramId, @NonNull String phone) {
        log.info("Saving phone {} for user with telegram id {}", phone, telegramId);
        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "User with telegram id %s not found", telegramId));
        user.setPhoneNumbers(Collections.singletonList(phone));
        return new UserDto(userRepository.save(user));
    }

    private User update(User oldUser, UserCreateDto createDto) {
        log.info("Updating user {}", createDto);

        return userRepository.save(
                User.from(oldUser, createDto)
        );
    }

    private User save(UserCreateDto createDto) {
        log.info("Saving user {}", createDto);

        return userRepository.save(
                User.from(createDto)
        );
    }

    @Override
    public UserDto getById(Long id) {
        log.info("Getting user by id {}", id);
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "User with id %s not found", id));
    }

    @Override
    public UserDto getByTelegramId(Long telegramId) {
        log.info("Getting user by telegram id {}", telegramId);
        return userRepository.findByTelegramId(telegramId)
                .map(UserDto::new)
                .orElseThrow(() -> new FindGuideException(ResponseCode.NOT_FOUND, "User with telegram id %s not found", telegramId));
    }
}
