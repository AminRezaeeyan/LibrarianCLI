package com.librarian.exceptions;

public class InvalidInputException extends LibraryServiceException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super("Invalid command");
    }
}
