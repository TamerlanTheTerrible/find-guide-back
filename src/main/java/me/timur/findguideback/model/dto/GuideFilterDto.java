package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GuideFilterDto extends BaseFilter {
    private Long userId;
    private Long telegramId;
    private String language;
    private String region;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    private LocalDateTime fromDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    private LocalDateTime toDate;
    private Boolean hasCar;
    private String comment;

    @Override
    public String toString() {
        return "GuideFilterDto{" +
                "userId=" + userId +
                ", telegramId=" + telegramId +
                ", language='" + language + '\'' +
                ", region='" + region + '\'' +
//                ", fromDate=" + fromDate +
//                ", toDate=" + toDate +
                ", hasCar=" + hasCar +
                ", comment='" + comment + '\'' +
                '}';
    }
}
