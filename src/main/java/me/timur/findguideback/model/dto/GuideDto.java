package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.entity.Guide;
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
    private Long id;
    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateCreated;
    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateUpdated;
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("language_names")
    private Set<String> languageNames;
    @JsonProperty("region_names")
    private Set<String> regionNames;
    @JsonProperty("files")
    private List<FileDto> files;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_verified")
    private Boolean isVerified;
    @JsonProperty("has_car")
    private Boolean hasCar;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    public GuideDto(Guide guide) {
        this.id = guide.getId();
        this.dateCreated = guide.getDateCreated();
        this.dateUpdated = guide.getDateUpdated();
        this.user = new UserDto(guide.getUser());
        this.languageNames = guide.getLanguageNames();
        this.regionNames = guide.getRegionNames();
        this.files = guide.getFiles() == null ? null : guide.getFiles().stream().map(FileDto::new).collect(Collectors.toList());
        this.description = guide.getDescription();
        this.isVerified = guide.getIsVerified();
        this.hasCar = guide.getHasCar();
        this.isActive = guide.getIsActive();
        this.isBlocked = guide.getIsBlocked();
    }

    @Override
    public String toString() {
        return "GuideDto{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", telegramUsername=" + user.getTelegramUsername() +
                ", phone=" + user.getPhoneNumbers() +
                ", languageNames=" + languageNames +
                ", regionNames=" + regionNames +
                ", files=" + files +
                ", description='" + description + '\'' +
                ", isVerified=" + isVerified +
                ", hasCar=" + hasCar +
                ", isActive=" + isActive +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
