package com.example.parking.Reposetories;

import com.example.parking.Entities.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface VehiclesRepo extends JpaRepository<Vehicles, String> {
        // מציאת כל הרכבים של משתמש ספציפי
        List<Vehicles> findByUser_UserId(long userId);


        //List<Vehicles> findByUserId(long userId, Pageable pageable);
        //List<Vehicles> findByVehicleOrderBy

        // האם הרכב הזה כבר רשום במערכת?
        Optional<Vehicles> findByLicensePlate(String licensePlate);


    }