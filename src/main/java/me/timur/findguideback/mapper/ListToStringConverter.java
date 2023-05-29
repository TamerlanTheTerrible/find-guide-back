package me.timur.findguideback.mapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 08/02/22.
 */

@Converter
public class ListToStringConverter<T> implements AttributeConverter<List<T>, String> {

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<T> list) {
        if (list == null) return null;
        return list.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    @SneakyThrows
    @Override
    public List<T> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        return (List<T>) Arrays.asList(s.split(","));
    }
}
