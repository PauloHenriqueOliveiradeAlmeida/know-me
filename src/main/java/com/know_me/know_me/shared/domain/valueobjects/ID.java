package com.know_me.know_me.shared.domain.valueobjects;

import java.util.UUID;

public record ID(UUID value) {
    public ID {
        if (value == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    public static ID random() {
        return new ID(UUID.randomUUID());
    }

    public static ID fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return new ID(UUID.fromString(value));
    }
}
