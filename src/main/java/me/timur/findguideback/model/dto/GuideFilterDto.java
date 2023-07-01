package me.timur.findguideback.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GuideFilterDto extends BaseFilter {
    private Long userId;
    private Long telegramId;
    private String language;
    private String region;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate toDate;
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
