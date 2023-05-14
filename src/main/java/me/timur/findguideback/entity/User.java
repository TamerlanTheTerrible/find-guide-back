package me.timur.findguideback.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import me.timur.findguideback.mapper.ListToStringConverter;
import me.timur.findguideback.model.dto.UserCreateDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthDate")
    private LocalDateTime birthDate;

    @Column(name = "tg_id", unique = true)
    private Long telegramId;

    @Column(name = "tg_username")
    private String telegramUsername;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "phone_numbers", nullable = false)
    private List<String> phoneNumbers;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    private Boolean isBlocked;

    public static User from(UserCreateDto createDto) {
        return User.builder()
                .firstName(createDto.getFirstName())
                .lastName(createDto.getLastName())
                .birthDate(createDto.getBirthDate())
                .telegramId(createDto.getTelegramId())
                .telegramUsername(createDto.getTelegramUsername())
                .phoneNumbers(createDto.getPhoneNumbers())
                .isActive(true)
                .isBlocked(false)
                .build();
    }

    public static User from(User user, UserCreateDto createDto) {
        user.setFirstName(createDto.getFirstName());
        user.setLastName(createDto.getLastName());
        user.setBirthDate(createDto.getBirthDate());
        user.setTelegramId(createDto.getTelegramId());
        user.setTelegramUsername(createDto.getTelegramUsername());
        user.setPhoneNumbers(createDto.getPhoneNumbers());
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", telegramId=" + telegramId +
                ", telegramUsername='" + telegramUsername + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", isActive=" + isActive +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
