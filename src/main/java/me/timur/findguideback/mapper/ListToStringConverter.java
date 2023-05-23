package me.timur.findguideback.mapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;
import me.timur.findguideback.util.StringUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 08/02/22.
 */

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null) return null;
        return String.join(",", list);
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String s) {
        return StringUtil.splitToList(s, ",");
    }
}
