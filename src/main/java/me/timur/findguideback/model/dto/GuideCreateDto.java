package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proto.ProtoGuideCreateDto;
import lombok.Data;
import me.timur.findguideback.util.StringUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class GuideCreateDto implements Serializable {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_telegram_id")
    private Long userTelegramId;
    @JsonProperty("language_names")
    private Set<String> languageNames;
    @JsonProperty("region_names")
    private Set<String> regionNames;
    @JsonProperty("files")
    private Set<FileCreateDto> files;
    @JsonProperty("description")
    private String description;
    @JsonProperty("has_car")
    private Boolean hasCar;

    public GuideCreateDto(ProtoGuideCreateDto request) {
        this.userId = request.getUserId();
        this.userTelegramId = request.getUserTelegramId();
        this.languageNames = StringUtil.splitToSet(request.getLanguageNames(), ",");
        this.regionNames = StringUtil.splitToSet(request.getRegionNames(), ",");
        this.description = request.getDescription();
        this.hasCar = request.getHasCar();
    }
}
