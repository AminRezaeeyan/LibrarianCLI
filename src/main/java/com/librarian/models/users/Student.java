package com.librarian.models.users;

public abstract class Student extends User {
    public Student(String id, String firstname, String lastname, String nationalId, String birthYear, String address,int borrowCount, long penalty) {
        super(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty);
    }
}
