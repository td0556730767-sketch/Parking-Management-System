package com.example.parking.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @ToString
    @Builder

    @Entity
    public class Users {
        @Id
        private long userId;
        private String fullName;
        private String phoneNumber;
        private double balance; // יתרה כספית בחשבון
        private int stars; // לצבירת נקודות הטבה


    }

