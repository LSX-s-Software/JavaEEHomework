package edu.whu.homework8.entity;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return String.join(",", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if("".equals(joined)){ return new ArrayList<>(); }
        return new ArrayList<>(Arrays.asList(joined.split(",")));
    }
}
