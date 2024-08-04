package com.librarian.models.users;

public class UndergraduateStudent extends Student {
    public UndergraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address, int borrowCount, long penalty) {
        super(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty);
    }
}
