package com.example.parking.Entities;

import jakarta.persistence.*;
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
        @Column(nullable = false)
        private String phoneNumber;
        private double balance; // יתרה כספית בחשבון
        private int stars;// לצבירת נקודות הטבה
        @Builder.Default
        @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
        private String role = "USER";


    }

