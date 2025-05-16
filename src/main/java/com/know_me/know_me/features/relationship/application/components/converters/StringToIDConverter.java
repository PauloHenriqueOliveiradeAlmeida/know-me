package com.know_me.know_me.features.relationship.application.components.converters;

import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIDConverter implements Converter<String, ID> {

    @Override
    public ID convert(String source) {
        return ID.fromString(source);
    }
}
