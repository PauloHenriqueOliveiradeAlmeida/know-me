package com.know_me.know_me.features.relationship.application.exceptions;
import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
    public NotFoundException(String message) {
        super(message.isEmpty() ? "Not found" : message);
    }
}
