package com.know_me.know_me.features.relationship.application.exceptions;

public class InvalidInputException extends IllegalArgumentException {
	public InvalidInputException(String message) {
		super(message.isEmpty() ? "Invalid input" : message);
	}
}
