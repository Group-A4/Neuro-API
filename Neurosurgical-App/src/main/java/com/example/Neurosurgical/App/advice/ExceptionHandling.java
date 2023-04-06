package com.example.Neurosurgical.App.advice;


import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExistsException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
