package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.timur.findguideback.entity.GuideSearch;
import me.timur.findguideback.model.enums.SearchStatus;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link me.timur.findguideback.entity.GuideSearch} entity
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GuideSearchDto implements Serializable {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateCreated;
    private Long clientId;
    private Long clientTelegramId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime toDate;
    private String language;
    private String region;
    private Boolean hasCar;
    private String comment;
    private List<Long> guideIds;
    private Long searchCount;
    private SearchStatus status;

    public GuideSearchDto(GuideSearch entity) {
        this.id = entity.getId();
        this.dateCreated = entity.getDateCreated();
        this.clientId = entity.getClient().getId();
        this.clientTelegramId = entity.getClient().getTelegramId();
        this.fromDate = entity.getFromDate();
        this.toDate = entity.getToDate();
        this.language = entity.getLanguage();
        this.region = entity.getRegion();
        this.hasCar = entity.getHasCar();
        this.comment = entity.getComment();
        this.guideIds = entity.getGuideIds();
        this.searchCount = entity.getSearchCount();
        this.status = entity.getStatus();
    }
}