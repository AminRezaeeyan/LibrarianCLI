package com.librarian.models.users;

public abstract class User {
    private final String id;
    private String firstname, lastname, NationalId, birthYear, address;
    private Long penalty;
    private int borrowCount;

    public User(String id, String firstname, String lastname, String nationalId, String birthYear, String address, int borrowCount, long penalty) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        NationalId = nationalId;
        this.birthYear = birthYear;
        this.address = address;
        this.borrowCount = borrowCount;
        this.penalty = penalty;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationalId() {
        return NationalId;
    }

    public void setNationalId(String nationalId) {
        NationalId = nationalId;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPenalty() {
        return penalty;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void borrowResource() {
        borrowCount++;
    }

    public void returnResource(long penalty) {
        borrowCount--;
        this.penalty += penalty;
    }

    public boolean hasBorrowed() {
        return borrowCount > 0;
    }

    public boolean hasPenalty() {
        return penalty > 0;
    }
}
