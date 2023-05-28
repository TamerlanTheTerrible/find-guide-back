package me.timur.findguideback.util;

import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Temurbek Ismoilov on 30/08/22.
 */

public class LocalDateTimeUtil {
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

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

        try {
            return LocalDateTime.parse(birthDate, formatter);
        } catch (Exception e) {
            throw new FindGuideException(ResponseCode.INVALID_PARAMETERS, "Invalid date format: %s", birthDate);
        }
    }
}
