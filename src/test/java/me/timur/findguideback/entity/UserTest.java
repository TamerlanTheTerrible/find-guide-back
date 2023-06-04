package me.timur.findguideback.entity;

import me.timur.findguideback.model.dto.UserCreateDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testFromUserCreateDto() {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setFirstName("John");
        createDto.setLastName("Doe");
        createDto.setBirthDate(LocalDateTime.now());
        createDto.setTelegramId(123456L);
        createDto.setTelegramUsername("johndoe");
        createDto.setPhoneNumbers(Arrays.asList("1234567890", "9876543210"));

        User user = User.from(createDto);

        assertEquals(createDto.getFirstName(), user.getFirstName());
        assertEquals(createDto.getLastName(), user.getLastName());
        assertEquals(createDto.getBirthDate(), user.getBirthDate());
        assertEquals(createDto.getTelegramId(), user.getTelegramId());
        assertEquals(createDto.getTelegramUsername(), user.getTelegramUsername());
        assertEquals(createDto.getPhoneNumbers(), user.getPhoneNumbers());
        assertEquals(true, user.getIsActive());
        assertEquals(false, user.getIsBlocked());
    }

    @Test
    public void testFromUserAndUserCreateDto() {
        User existingUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDateTime.now())
                .telegramId(123456L)
                .telegramUsername("johndoe")
                .phoneNumbers(Arrays.asList("1234567890", "9876543210"))
                .isActive(true)
                .isBlocked(false)
                .build();

        UserCreateDto createDto = new UserCreateDto();
        createDto.setFirstName("Jane");
        createDto.setLastName("Smith");
        createDto.setBirthDate(LocalDateTime.now());
        createDto.setTelegramId(987654L);
        createDto.setTelegramUsername("janesmith");
        createDto.setPhoneNumbers(Arrays.asList("1111111111", "2222222222"));

        User updatedUser = User.from(existingUser, createDto);

        assertEquals(createDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(createDto.getLastName(), updatedUser.getLastName());
        assertEquals(createDto.getBirthDate(), updatedUser.getBirthDate());
        assertEquals(createDto.getTelegramId(), updatedUser.getTelegramId());
        assertEquals(createDto.getTelegramUsername(), updatedUser.getTelegramUsername());
        assertEquals(createDto.getPhoneNumbers(), updatedUser.getPhoneNumbers());
        assertEquals(existingUser.getIsActive(), updatedUser.getIsActive());
        assertEquals(existingUser.getIsBlocked(), updatedUser.getIsBlocked());
    }
}

