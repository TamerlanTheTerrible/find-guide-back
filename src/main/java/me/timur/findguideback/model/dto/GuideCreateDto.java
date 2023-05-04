package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class GuideCreateDto implements Serializable {
    @JsonProperty("user")
    private UserCreateDto user;
    @JsonProperty("language_names")
    private Set<String> languageNames;
    @JsonProperty("region_names")
    private Set<String> regionNames;
    @JsonProperty("files")
    private Set<FileCreateDto> files;
    @JsonProperty("description")
    private String description;
}
