package me.timur.findguideback.mapper;

import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 08/02/22.
 */

@Converter
public class StringListToStringConverter implements AttributeConverter<List<String>, String> {

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null) return null;
        return list.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(s.split(","));
    }
}
