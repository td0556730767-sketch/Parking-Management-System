package com.example.parking.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingException extends RuntimeException{

    public ParkingException(String message) {
        super(message); // מעביר את ההודעה למחלקה הראשית של Java שתדע לטפל בה
    }
}
