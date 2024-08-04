package com.librarian.models.resources;

import java.time.Duration;

public class BorrowableBook extends Resource implements Borrowable {
    private String publisher, publicationYear;
    private int copiesCount, borrowedCopiesCount, totalBorrowsCount;
    private Duration totalBorrowsDuration;

    public BorrowableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, int copiesCount, int borrowedCopiesCount, int totalBorrowsCount, Duration totalBorrowsDuration) {
        super(id, libraryId, categoryId, title, author);
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.copiesCount = copiesCount;
        this.borrowedCopiesCount = borrowedCopiesCount;
        this.totalBorrowsCount = totalBorrowsCount;
        this.totalBorrowsDuration = totalBorrowsDuration;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getCopiesCount() {
        return copiesCount;
    }

    public void setCopiesCount(int copiesCount) {
        this.copiesCount = copiesCount;
    }

    public int getBorrowedCopiesCount() {
        return borrowedCopiesCount;
    }

    public void setBorrowedCopiesCount(int borrowedCopiesCount) {
        this.borrowedCopiesCount = borrowedCopiesCount;
    }

    @Override
    public int getTotalBorrowsCount() {
        return totalBorrowsCount;
    }

    public void setTotalBorrowsCount(int totalBorrowsCount) {
        this.totalBorrowsCount = totalBorrowsCount;
    }

    public Duration getTotalBorrowsDuration() {
        return totalBorrowsDuration;
    }

    public void setTotalBorrowsDuration(Duration totalBorrowsDuration) {
        this.totalBorrowsDuration = totalBorrowsDuration;
    }

    @Override
    public boolean isAvailable() {
        return copiesCount > borrowedCopiesCount;
    }

    @Override
    public boolean isBorrowed() {
        return borrowedCopiesCount > 0;
    }

    @Override
    public void borrowResource() {
        borrowedCopiesCount++;
        totalBorrowsCount++;
    }

    @Override
    public void returnResource(Duration borrowDuration) {
        borrowedCopiesCount--;
        totalBorrowsDuration = totalBorrowsDuration.plus(borrowDuration);
    }
}
