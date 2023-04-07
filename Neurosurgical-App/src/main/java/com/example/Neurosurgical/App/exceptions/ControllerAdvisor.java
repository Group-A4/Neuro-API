package com.example.Neurosurgical.App.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(value = CannotRemoveLastAdminException.class)
    public ResponseEntity<ErrorResponse> handleCannotRemoveLastAdminException(CannotRemoveLastAdminException e){
        return ResponseEntity.status(403).body(new ErrorResponse(e.getMessage()));
    }

}
