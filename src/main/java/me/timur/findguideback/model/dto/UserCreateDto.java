package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class UserCreateDto {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthDate;
    @JsonProperty("telegram_id")
    private Long telegramId;
    @JsonProperty("telegram_username")
    private String telegramUsername;
    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;

    @Override
    public String toString() {
        return "UserCreateDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", telegramId=" + telegramId +
                ", telegramUsername='" + telegramUsername + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
