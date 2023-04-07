package com.example.Neurosurgical.App.advice.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
