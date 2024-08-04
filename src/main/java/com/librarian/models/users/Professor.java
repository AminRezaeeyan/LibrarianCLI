package com.librarian.models.users;

public class Professor extends User {
    private String department;

    public Professor(String id, String firstname, String lastname, String nationalId, String birthYear, String address,int borrowCount, long penalty, String department) {
        super(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
