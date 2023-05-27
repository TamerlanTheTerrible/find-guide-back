package me.timur.findguideback.util;

import me.timur.findguideback.exception.FindGuideException;
import me.timur.findguideback.model.enums.ResponseCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

public class StringUtil {

    public static List<String> splitToList(String str) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return splitToList(str, ",", null);
    }

    public static List<String> splitToList(String str, StringFormat format) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return splitToList(str, ",", format);
    }

    public static List<String> splitToList(String str, String delimiter) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return splitToList(str, delimiter, null);
    }

    public static List<String> splitToList(String str, String delimiter, StringFormat format) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }

        if (format != null && !str.replaceAll(" ", "").matches(format.regex)) {
            throw new FindGuideException(ResponseCode.INVALID_PARAMETERS, "Invalid format for %s: %s", format, str);
        }

        return Arrays.stream(str.split(delimiter)).toList();
    }

    public static Set<String> splitToSet(String str, String delimiter) {
        if (str == null || str.isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(delimiter)).collect(Collectors.toSet());
    }

    public static String join(Iterable<String> elements) {
        if (elements == null || !elements.iterator().hasNext()) {
            return "";
        }
        return String.join(",", elements);
    }

    public enum StringFormat {
        PHONE("\\d{12}")
        ;

        public final String regex;

        StringFormat(String regex) {
            this.regex = regex;
        }
    }
}
