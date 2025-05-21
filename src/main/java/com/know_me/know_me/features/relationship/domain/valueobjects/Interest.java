package com.know_me.know_me.features.relationship.domain.valueobjects;

public class Interest {
    private final String value;
    public Interest(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Nome do interesse deve ser preenchido");
        }
        this.value = value.toLowerCase();
    }

    public String value() {
        return value;
    }

    public String formattedValue() {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
