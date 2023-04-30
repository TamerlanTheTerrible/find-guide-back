package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class UserDto implements Serializable {
    private final Long id;

    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateCreated;

    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateUpdated;

    @JsonProperty("first_name")
    private final String firstName;
    @JsonProperty("last_name")
    private final String lastName;
    @JsonProperty("birth_date")
    private final LocalDateTime birthDate;
    @JsonProperty("telegram_id")
    private final Long telegramId;
    @JsonProperty("phone_numbers")
    private final List<List<String>> phoneNumbers;
    @JsonProperty("is_active")
    private final Boolean isActive;
    @JsonProperty("is_blocked")
    private final Boolean isBlocked;

}
