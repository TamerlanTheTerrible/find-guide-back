package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.entity.User;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class UserDto implements Serializable {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateCreated;
    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateUpdated;
    @JsonProperty("first_name")
    private final String firstName;
    @JsonProperty("last_name")
    private final String lastName;
    @JsonProperty("birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime birthDate;
    @JsonProperty("telegram_id")
    private final Long telegramId;
    @JsonProperty("phone_numbers")
    private final List<String> phoneNumbers;
    @JsonProperty("is_active")
    private final Boolean isActive;
    @JsonProperty("is_blocked")
    private final Boolean isBlocked;

    public UserDto(User user) {
        this.id = user.getId();
        this.dateCreated = user.getDateCreated();
        this.dateUpdated = user.getDateUpdated();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.telegramId = user.getTelegramId();
        this.phoneNumbers = user.getPhoneNumbers();
        this.isActive = user.getIsActive();
        this.isBlocked = user.getIsBlocked();
    }
}
