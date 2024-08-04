package com.librarian.models.users;

public class Staff extends User {
    private String role;

    public Staff(String id, String firstname, String lastname, String nationalId, String birthYear, String address, int borrowCount, long penalty, String role) {
        super(id, firstname, lastname, nationalId, birthYear, address,borrowCount, penalty);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
