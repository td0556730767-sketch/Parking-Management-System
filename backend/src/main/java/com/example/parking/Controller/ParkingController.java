package com.example.parking.Controller;

import com.example.parking.Entities.Parking;
import com.example.parking.Entities.Users;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking") // הכתובת הבסיסית
public class ParkingController {



    private final ParkingService parkingService;
    @GetMapping("/getAllParking")
    public List<Parking> getAllParking() {
        return parkingService.getAllParkings();
    }
    @GetMapping("/getAllParkingAvailable")
    public List<Parking> getAllParkingAvailable() {
        return parkingService.getAllParkingAvailable();
    }

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }
    @PostMapping("/add")
    public String addParking(@RequestBody Parking parking) {
        parkingService.addParking(parking);
        return "Parking " + parking.getLocationX()+","+parking.getLocationY()+ " added successfully to User " + parking.getSpotId();
    }

//    /**
//     * פונקציית הכניסה לחניון
//     * הנהג או המערכת שולחים בקשת POST עם מספר הרכב
//     * דוגמה לכתובת: http://localhost:8080/api/parking/enter/1234567
//     */
//    @PostMapping("/enter/{vehicleId}")
//    public String enterParking(@PathVariable String vehicleId) {
//        // הקונטרולר קורא לפונקציה ב-Service שמעדכנת את הכל
//        return parkingService.startParking(vehicleId);
//    }
//
//    /**
//     * יציאה מהחניון
//     * המערכת תחשב זמן חניה, תבדוק אם מגיעה הנחה, ותשחרר את המקום
//     * דוגמה לכתובת: http://localhost:8080/api/parking/exit/1234567
//     */
//    @PostMapping("/exit/{vehicleId}")
//    public String exitParking(@PathVariable String vehicleId) {
//        // קריאה לסרוויס שיבצע את כל חישובי הכסף והשחרור
//        return parkingService.endParking(vehicleId);
//    }


//    @PostMapping("/enter/{vehicleId}")
//    public String enterParking(@PathVariable String vehicleId) {
//        // הקונטרולר קורא לפונקציה ב-Service שמעדכנת את הכל
//        return parkingService.startParking(vehicleId);
//    }

    /**
     * יציאה מהחניון
     * המערכת תחשב זמן חניה, תבדוק אם מגיעה הנחה, ותשחרר את המקום
     * דוגמה לכתובת: http://localhost:8080/api/parking/exit/1234567
     */
//    @PostMapping("/exit/{vehicleId}")
//    public String exitParking(@PathVariable String vehicleId) {
//        // קריאה לסרוויס שיבצע את כל חישובי הכסף והשחרור
//        return parkingService.endParking();
//    }



}