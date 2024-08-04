package com.librarian.models;

public class Library {
    private final String id;
    private String name, address, establishYear;
    private int tableCount;

    public Library(String id, String name, String address, String establishYear, int tableCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.establishYear = establishYear;
        this.tableCount = tableCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEstablishYear() {
        return establishYear;
    }

    public void setEstablishYear(String establishYear) {
        this.establishYear = establishYear;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }
}
