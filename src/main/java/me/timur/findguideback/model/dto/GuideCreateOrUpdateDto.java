package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proto.ProtoGuideCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.timur.findguideback.util.StringUtil;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuideCreateOrUpdateDto implements Serializable {
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
    @JsonProperty("files")
    private Set<String> transports;

    public GuideCreateOrUpdateDto(ProtoGuideCreateDto request) {
        this.userId = request.getUserId();
        this.userTelegramId = request.getUserTelegramId();
        this.languageNames = StringUtil.splitToSet(request.getLanguageNames(), ",");
        this.regionNames = StringUtil.splitToSet(request.getRegionNames(), ",");
        this.description = request.getDescription();
        this.hasCar = request.getHasCar();
        this.transports = StringUtil.splitToSet(request.getTransport(), ",");
    }
}
