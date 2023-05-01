package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class FileCreateDto implements Serializable {
    @JsonProperty("type")
    private final DocumentType type;
    @JsonProperty("path")
    private final String path;
    @JsonProperty("extension")
    private final DocumentExtension extension;
    @JsonProperty("size")
    private final Long size;
}
