package me.timur.findguideback.bot.guide.model.dto;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Temurbek Ismoilov on 28/03/23.
 */

@Getter
public class NewGuideProgress {
    private boolean languageProgressing;
    private boolean regionProgressing;
    private boolean carProgressing;
    private boolean commentProgressing;
    private Set<String> languages;
    private Set<String> regions;
    private boolean hasCar;
    private String comment;

    public NewGuideProgress() {
        languageProgressing = true;
        this.languages = new HashSet<>();
        this.regions = new HashSet<>();

    }

    public void languageIsProcessing() {
        this.languageProgressing = true;
        this.regionProgressing = false;
        this.carProgressing = false;
        this.commentProgressing = false;
    }

    public void regionProcessing() {
        this.languageProgressing = false;
        this.regionProgressing = true;
        this.carProgressing = false;
        this.commentProgressing = false;
    }

    public void carProcessing() {
        this.languageProgressing = false;
        this.regionProgressing = false;
        this.carProgressing = true;
        this.commentProgressing = false;
    }

    public void commentProcessing() {
        this.languageProgressing = false;
        this.regionProgressing = false;
        this.carProgressing = false;
        this.commentProgressing = true;
    }

    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public void addRegion(String region) {
        this.regions.add(region);
    }
}