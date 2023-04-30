package me.timur.findguideback.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Temurbek Ismoilov on 30/08/22.
 */

public class LocalDateTimeUtil {
    public final static String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String toString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return dateTime.format(formatter);
    }
}
