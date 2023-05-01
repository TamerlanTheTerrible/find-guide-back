package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.FileDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class GuideDto implements Serializable {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateCreated;
    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime dateUpdated;
    @JsonProperty("user")
    private final UserDto user;
    @JsonProperty("language_names")
    private final Set<String> languageNames;
    @JsonProperty("region_names")
    private final Set<String> regionNames;
    @JsonProperty("files")
    private final List<FileDto> files;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("is_verified")
    private final Boolean isVerified;
    @JsonProperty("is_active")
    private final Boolean isActive;
    @JsonProperty("is_blocked")
    private final Boolean isBlocked;

    public GuideDto(Guide guide) {
        this.id = guide.getId();
        this.dateCreated = guide.getDateCreated();
        this.dateUpdated = guide.getDateUpdated();
        this.user = new UserDto(guide.getUser());
        this.languageNames = guide.getLanguageNames();
        this.regionNames = guide.getRegionNames();
        this.files = guide.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.description = guide.getDescription();
        this.isVerified = guide.getIsVerified();
        this.isActive = guide.getIsActive();
        this.isBlocked = guide.getIsBlocked();
    }
}
