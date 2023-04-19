package com.example.Neurosurgical.App.advice.exceptions;

import java.nio.file.Watchable;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String entityName, String name) {
        super(String.format("%s %s already exists", entityName, name));
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }



}
