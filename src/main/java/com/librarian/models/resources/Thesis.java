package com.librarian.models.resources;

import java.time.Duration;

public class Thesis extends Resource implements Borrowable{
    private String advisorName, defenseYear;
    private int copiesCount, borrowedCopiesCount, totalBorrowsCount;
    private Duration totalBorrowsDuration;

    public Thesis(String id,  String libraryId, String categoryId, String title, String author, String advisorName, String defenseYear, int copiesCount, int borrowedCopiesCount, int totalBorrowsCount, Duration totalBorrowsDuration) {
        super(id, libraryId, categoryId, title, author);
        this.advisorName = advisorName;
        this.defenseYear = defenseYear;
        this.copiesCount = copiesCount;
        this.borrowedCopiesCount = borrowedCopiesCount;
        this.totalBorrowsCount = totalBorrowsCount;
        this.totalBorrowsDuration = totalBorrowsDuration;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getDefenseYear() {
        return defenseYear;
    }

    public void setDefenseYear(String defenseYear) {
        this.defenseYear = defenseYear;
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
