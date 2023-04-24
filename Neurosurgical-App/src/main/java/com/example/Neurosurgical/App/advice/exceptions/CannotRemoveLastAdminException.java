package com.example.Neurosurgical.App.advice.exceptions;

public class CannotRemoveLastAdminException extends RuntimeException{
    public CannotRemoveLastAdminException() {
        super("Cannot remove last admin.");
    }
}