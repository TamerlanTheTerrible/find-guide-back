package me.timur.findguideback.bot.client.model.dto;

import lombok.Data;
import me.timur.findguideback.model.enums.LanguageEnum;

/**
 * Created by Temurbek Ismoilov on 28/03/23.
 */

@Data
public class UserProgress {
    private boolean selectingLanguage;
    private boolean selectingRegion;
    private boolean selectingStartYear;
    private boolean selectingStartMonth;
    private boolean selectingStartDate;
    private boolean selectingEndYear;
    private boolean selectingEndMonth;
    private boolean selectingEndDate;
    private LanguageEnum languageEnum;
    private String region;
    private Integer startYear;
    private Integer startMonth;
    private Integer startDate;
    private Integer endYear;
    private Integer endMonth;
    private Integer endDate;

    public UserProgress() {
        selectingLanguage = true;
    }
}