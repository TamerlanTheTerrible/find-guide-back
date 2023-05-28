package me.timur.findguideback.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import me.timur.findguideback.util.LocalDateTimeUtil;

import java.time.LocalDate;

/**
 * Created by Temurbek Ismoilov on 02/05/23.
 */

public class GuideSearchDto {
    @JsonProperty("language")
    String language;

    @JsonProperty("region")
    String region;

    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate startDate;

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LocalDateTimeUtil.DATE_TIME_PATTERN) @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate endDate;
}
