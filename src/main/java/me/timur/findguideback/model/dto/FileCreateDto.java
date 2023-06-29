package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;

import java.io.Serializable;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileCreateDto implements Serializable {
    @JsonProperty("guide_id")
    private Long guideId;
    @JsonProperty("guide_telegram_id")
    private Long guideTelegramId;
    @JsonProperty("type")
    private DocumentType type;
    @JsonProperty("file_telegram_id")
    private String fileTelegramId;
    @JsonProperty("path")
    private String path;
    @JsonProperty("extension")
    private DocumentExtension extension;
    @JsonProperty("size")
    private Long size;

    @Override
    public String toString() {
        return "FileCreateDto{" +
                "guideId=" + guideId +
                ", guideTelegramId=" + guideTelegramId +
                ", type=" + type +
                ", fileTelegramId=" + fileTelegramId +
                ", path='" + path + '\'' +
                ", extension=" + extension +
                ", size=" + size +
                '}';
    }
}
