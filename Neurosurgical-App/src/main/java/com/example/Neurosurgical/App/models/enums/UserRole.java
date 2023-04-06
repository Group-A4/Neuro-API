package com.example.Neurosurgical.App.models.enums;

public enum UserRole {
    ADMIN(0),
    PROFESSOR(1),
    STUDENT(2);


    private final int value;

    private UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
