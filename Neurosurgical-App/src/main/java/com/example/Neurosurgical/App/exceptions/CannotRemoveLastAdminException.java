package com.example.Neurosurgical.App.exceptions;

public class CannotRemoveLastAdminException extends RuntimeException{
    public CannotRemoveLastAdminException() {
        super("Cannot remove last admin.");
    }
}