package com.librarian.exceptions;

public class NotAllowedException extends LibraryServiceException {
    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException() {
        super("not-allowed");
    }
}
