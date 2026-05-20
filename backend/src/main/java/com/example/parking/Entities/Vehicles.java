package com.example.parking.Entities;
import com.example.parking.Entities.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @ToString
    @Builder
    @Entity

    public class Vehicles {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long vehicleId;

        @Column(unique = true, nullable = false)
        private String licensePlate; // מספר רישוי הרכב

        @ManyToOne
        @JoinColumn(name = "user_id")
        private Users user;

        @Enumerated(EnumType.STRING)
        private VehicleType vehicleType;    // למשל: "נכים", "חשמלי", "רגיל"

        @OneToOne
        @JoinColumn(name = "spot_id" ,nullable= true )
        private Parking parking;         // מזהה החניה שבה הרכב חנה (מצביע על ParkingSpot)



    }
