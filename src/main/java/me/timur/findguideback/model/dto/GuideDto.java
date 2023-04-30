package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.model.dto.FileDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class GuideDto implements Serializable {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateCreated;

    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateUpdated;

    @JsonProperty("user")
    private final UserDto user;
    @JsonProperty("language_names")
    private final Set<String> languageNames;
    @JsonProperty("region_names")
    private final Set<String> regionNames;
    @JsonProperty("files")
    private final Set<FileDto> files;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("is_verified")
    private final Boolean isVerified;
    @JsonProperty("is_active")
    private final Boolean isActive;
    @JsonProperty("is_blocked")
    private final Boolean isBlocked;
}
