package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.timur.findguideback.bot.guide.model.dto.NewGuideProgress;

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
    @JsonProperty("transport")
    private Set<String> transports;

    public GuideCreateOrUpdateDto(Long userTelegramId, NewGuideProgress progress) {
        this.userTelegramId = userTelegramId;
        this.languageNames = progress.getLanguages();
        this.regionNames = progress.getRegions();
        this.hasCar = progress.isHasCar();
        this.description = progress.getComment();
    }
}
