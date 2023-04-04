package com.example.Neurosurgical.App.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String msg) {
        super("User already exists : "+msg);
    }
}
