package com.example.Neurosurgical.App.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User not found");
    }

}
