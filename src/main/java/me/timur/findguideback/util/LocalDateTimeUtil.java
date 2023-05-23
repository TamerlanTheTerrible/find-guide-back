package me.timur.findguideback.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Temurbek Ismoilov on 30/08/22.
 */

public class LocalDateTimeUtil {
    public final static String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

    public static String toString(LocalDateTime dateTime){
        if (dateTime == null)
            return null;
        return dateTime.format(formatter);
    }

    public static String toStringOrBlankIfNull(LocalDateTime dateTime){
        if (dateTime == null)
            return "";
        return dateTime.format(formatter);
    }

    public static LocalDateTime toLocalDateTime(String birthDate) {
        if (birthDate == null || birthDate.isEmpty())
            return null;
        return LocalDateTime.parse(birthDate, formatter);
    }
}
