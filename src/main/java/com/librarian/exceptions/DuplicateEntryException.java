package com.librarian.exceptions;

public class DuplicateEntryException extends LibraryServiceException {
    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException() {
        super("duplicate-id");
    }
}
