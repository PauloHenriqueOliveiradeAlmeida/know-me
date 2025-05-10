package com.know_me.know_me.features.relationship.domain.valueobjects;

public record Interest(String name) {
    public Interest {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome do interesse deve ser preenchido");
        }
    }
}
