package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;

import java.io.Serializable;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class FileCreateDto implements Serializable {
    @JsonProperty("type")
    private DocumentType type;
    @JsonProperty("path")
    private String path;
    @JsonProperty("extension")
    private DocumentExtension extension;
    @JsonProperty("size")
    private Long size;
}
