package com.example.Neurosurgical.App.advice.exceptions;

public class QuestionNotFound extends RuntimeException {
    public QuestionNotFound(){
        super("Question has not been found");
    }
}
