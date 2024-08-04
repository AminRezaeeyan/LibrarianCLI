package com.librarian.models.users;

public class GraduateStudent extends Student {
    private String advisorName;

    public GraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address, int borrowCount, long penalty, String advisorName) {
        super(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty);
        this.advisorName = advisorName;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }
}
