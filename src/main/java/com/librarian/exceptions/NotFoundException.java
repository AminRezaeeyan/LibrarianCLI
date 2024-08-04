package com.librarian.exceptions;

public class NotFoundException extends LibraryServiceException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("not-found");
    }
}
