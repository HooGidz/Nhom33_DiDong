package com.example.nhom33.database;

public class Address {
    private String label;
    private String address;

    public Address(String label, String address) {
        this.label = label;
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public String getAddress() {
        return address;
    }

}
