package com.example.Neurosurgical.App.advice.exceptions;

public class ExamQuestionNotFoundException extends RuntimeException{
    public ExamQuestionNotFoundException() {
        super("Exam question has not been found");
    }
}
