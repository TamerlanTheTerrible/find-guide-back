package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class GuideCreateDto implements Serializable {
    @JsonProperty("user")
    private final UserCreateDto user;
    @JsonProperty("language_names")
    private final Set<String> languageNames;
    @JsonProperty("region_names")
    private final Set<String> regionNames;
    @JsonProperty("files")
    private final Set<FileCreateDto> files;
    @JsonProperty("description")
    private final String description;
}
