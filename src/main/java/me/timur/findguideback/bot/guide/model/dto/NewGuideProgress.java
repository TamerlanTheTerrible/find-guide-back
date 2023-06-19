package me.timur.findguideback.bot.guide.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 28/03/23.
 */

@Getter
@Setter
public class NewGuideProgress {
    private boolean languageProgressing;
    private boolean regionProgressing;
    private boolean carProgressing;
    private boolean commentProgressing;
    private Set<String> languages;
    private Set<String> region;
    private boolean hasCar;
    private String comment;

    public NewGuideProgress() {
        languageProgressing = true;
        this.languages = new HashSet<>();
        this.region = new HashSet<>();

    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public void addRegion(String region) {
        this.region.add(region);
    }
}