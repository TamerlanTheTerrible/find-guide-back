package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import me.timur.findguideback.entity.File;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Data
public class FileDto implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateCreated;
    @JsonProperty("date_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateUpdated;
    @JsonProperty("type")
    private DocumentType type;
    @JsonProperty("path")
    private String path;
    @JsonProperty("extension")
    private DocumentExtension extension;
    @JsonProperty("size")
    private Long size;

    public FileDto(File file) {
        this.id = file.getId();
        this.dateCreated = file.getDateCreated();
        this.dateUpdated = file.getDateUpdated();
        this.type = file.getType();
        this.path = file.getPath();
        this.extension = file.getExtension();
        this.size = file.getSize();
    }
}
