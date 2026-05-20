package com.example.parking.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<String> handle(ParkingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
