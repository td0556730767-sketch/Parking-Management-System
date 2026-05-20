package com.example.parking.Controller;

import com.example.parking.Entities.Vehicles;
import com.example.parking.Service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehiclesController {

    private final VehiclesService vehiclesService;


    @PostMapping("/add")//הפעולה הזאת אחרי שלקוח עושה מנוי לחניה הוא צריך לרשום את הרכב שלו
    public String addVehicle(@RequestBody Vehicles vehicle) {
        vehiclesService.addVehicle(vehicle);
        return "Vehicle " + vehicle.getLicensePlate() + " added successfully to User " + vehicle.getUser();
    }

    /**
     * הצגת כל הרכבים הרשומים (לצורכי בקרה)
     */
    @GetMapping("/all")
    public List<Vehicles> getAllVehicles() {
        return vehiclesService.getAllVehicles();
    }

    /**
     * מציאת רכבים לפי ID של משתמש
     */
    @GetMapping("/user/{userId}")
    public List<Vehicles> getVehiclesByUser(@PathVariable long userId) {
        return vehiclesService.getVehiclesByUserId(userId);
    }
    @GetMapping("/allVehiclesInParking")
    public List<Vehicles> getAllVehiclesInParking() {
        return vehiclesService.allVehiclesInParking();
    }

}