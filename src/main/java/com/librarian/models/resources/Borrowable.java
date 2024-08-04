package com.librarian.models.resources;

import java.time.Duration;

public interface Borrowable {
    void borrowResource();

    void returnResource(Duration borrowDuration);

    boolean isAvailable();

    boolean isBorrowed();

    int getTotalBorrowsCount();
}
