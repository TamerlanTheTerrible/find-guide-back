package me.timur.findguideback.mapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 08/02/22.
 */

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return String.join(",", list);
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String s) {
        return List.of(s.split(","));
    }
}
