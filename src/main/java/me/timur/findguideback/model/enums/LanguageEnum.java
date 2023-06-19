package me.timur.findguideback.model.enums;

import me.timur.findguideback.bot.client.exception.ClinetException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Temurbek Ismoilov on 10/04/23.
 */

public enum LanguageEnum {
    RUSSIAN("Русский"),
    ENGLISH("English"),
    GERMAN("Deutsch"),
    ITALIAN("Italiano"),
    SPANISH("Español");

    public final String text;

    LanguageEnum(String text) {
        this.text = text;
    }

    public static LanguageEnum get(String text){
        return Arrays.stream(LanguageEnum.values())
                .filter(l -> Objects.equals(l.text, text))
                .findFirst()
                .orElseThrow(() -> new ClinetException("Could not find language: %s", text));
    }
}
