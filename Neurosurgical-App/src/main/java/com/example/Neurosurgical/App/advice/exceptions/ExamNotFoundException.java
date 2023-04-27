package com.example.Neurosurgical.App.advice.exceptions;

public class ExamNotFoundException extends RuntimeException{
    public ExamNotFoundException() {
        super("Exam has not been found");
    }
}
