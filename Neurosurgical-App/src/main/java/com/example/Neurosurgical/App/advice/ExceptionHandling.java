package com.example.Neurosurgical.App.advice;


import com.example.Neurosurgical.App.advice.exceptions.*;
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



    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException exception){
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handle(EntityAlreadyExistsException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(value = CannotRemoveLastAdminException.class)
    public ResponseEntity<ErrorResponse> handleCannotRemoveLastAdminException(CannotRemoveLastAdminException e){
        return ResponseEntity.status(403).body(new ErrorResponse(e.getMessage()));
    }


}
