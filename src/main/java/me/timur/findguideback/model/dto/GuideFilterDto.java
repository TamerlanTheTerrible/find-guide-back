package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.proto.ProtoGuideFilterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.time.LocalDateTime;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GuideFilterDto extends BaseFilter {
    private String language;
    private String region;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime to;
    private Boolean hasCar;
    private String comments;

    public GuideFilterDto(ProtoGuideFilterDto request) {
        this.language = request.getLanguageNames();
        this.region = request.getRegionNames();
        if (request.getFrom() == null || request.getFrom().isEmpty()) {
            this.from = LocalDateTime.now();
        } else {
            this.from = LocalDateTimeUtil.toLocalDateTime(request.getFrom());
        }
        if (request.getTo() == null || request.getTo().isEmpty()) {
            this.to = LocalDateTime.now();
        } else {
            this.to = LocalDateTimeUtil.toLocalDateTime(request.getTo());
        }
        this.hasCar = request.getHasCar();
        this.comments = request.getComment();
    }
}
