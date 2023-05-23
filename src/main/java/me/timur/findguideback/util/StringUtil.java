package me.timur.findguideback.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 14/05/23.
 */

public class StringUtil {
    public static List<String> splitToList(String str, String delimiter) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
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
}
