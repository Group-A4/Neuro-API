package com.example.Neurosurgical.App.advice.exceptions;

public class MaterialNotFoundException extends RuntimeException{
    public MaterialNotFoundException() {
        super("Material has not been found");
    }
}
