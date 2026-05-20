package com.example.parking.Entities;

import com.example.parking.Entities.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @ToString
    @Builder
    @Entity
    public class Transactions {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long transactionId;
        private String licensePlate;

        @ManyToOne
        @JoinColumn(name = "vehicleId", nullable = true)
        private Vehicles vehicle;

        @ManyToOne
        @JoinColumn(name = "spotId", nullable = false) // שם העמודה בטבלה יישאר spotId, אבל ב-Java זה אובייקט
        private Parking parking;
        // מספר החניה
        private LocalDateTime startTime;
        @JoinColumn(nullable = true)// זמן כניסה (שניות/מילישניות)
        private LocalDateTime endTime;        // זמן יציאה
        private double totalPayment; // הסכום שחושב בסוף
        private boolean paymentStatus; // האם התשלום בוצע (TRUE / FALSE)

        @Enumerated(EnumType.STRING)
        private TransactionStatus status;

        @ManyToOne
        @JoinColumn(name = "userId" , nullable = true)
        private Users user; // יכול להיות Null אם הרכב לא רשום


    }

