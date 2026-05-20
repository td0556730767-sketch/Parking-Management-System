package com.example.parking.Service;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Reposetories.VehiclesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class VehiclesService {

    private final VehiclesRepo vehiclesRepo;

    public VehiclesService(VehiclesRepo vehiclesRepo) {
        this.vehiclesRepo = vehiclesRepo;
    }
    //כל הרכבים שנמצאים כרגע בחניה
    public List<Vehicles> allVehiclesInParking() {
        List<Vehicles> vehicles=vehiclesRepo.findAll();
        List<Vehicles>result=new ArrayList<Vehicles>();
        for(Vehicles vehicle:vehicles){
            if (vehicle.getVehicleId()!=0)//טעות
                result.add(vehicle);
        }
        return result;
    }

    // רישום רכב חדש במערכת
    public String registerVehicle(Vehicles vehicle) {
        if (vehiclesRepo.existsById(vehicle.getLicensePlate())) {
            return "Error: Vehicle already registered!";
        }
        vehiclesRepo.save(vehicle);
        return "Vehicle " + vehicle.getLicensePlate() + " registered successfully.";
    }
    public void addVehicle(Vehicles vehicle) {
        vehiclesRepo.save(vehicle);
    }

    // מציאת כל הרכבים של משתמש מסוים
    public List<Vehicles> getVehiclesByUserId(long userId) {
        // אם הוספת את הפונקציה הזו ל-Repository כפי שסיכמנו קודם
        return vehiclesRepo.findByUser_UserId(userId);
    }
    public List<Vehicles> getAllVehicles() {
        // אם הוספת את הפונקציה הזו ל-Repository כפי שסיכמנו קודם
        return vehiclesRepo.findAll();
    }
///ללללתקן
    /**
     * שיוך רכב למשתמש (עבור רישום רטרואקטיבי או עדכון בעלים)
     */
//    public void assignVehicleToUser(String vehicleId, long userId,  String vehicleType, // "HANDICAPPED", "TRUCK", "PRIVATE"
//                                     String licensePlate) {
//        // 1. ננסה למצוא את הרכב בבסיס הנתונים
//        Optional<Vehicles> vehicleOpt = vehiclesRepo.findById(vehicleId);
//
//        if (vehicleOpt.isPresent()) {
//            // אם הרכב קיים (למשל נרשם כ"אורח" בעבר), פשוט נעדכן לו את ה-userId
//            Vehicles vehicle = vehicleOpt.get();
//            vehicle.getUser.set(userId);
//            vehiclesRepo.save(vehicle);
//        } else {
//            // אם הרכב לא קיים בכלל בטבלת הרכבים (פעם ראשונה שאנחנו שומעים עליו)
//            // ניצור ישות רכב חדשה ונשייך אותה ליוזר
////            Vehicles newVehicle = Vehicles.builder()
////                    .licensePlate(licensePlate)
////                    .userId(userId)
////                    .vehicleType(vehicleType)
////                    .build();
////            vehiclesRepo.save(newVehicle);
//        }
//    }

}