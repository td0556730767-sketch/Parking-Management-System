package com.example.parking.Controller;
import com.example.parking.Entities.Users;
import com.example.parking.Service.ParkingService;
import com.example.parking.Service.UsersService;
import com.example.parking.Service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users") // הכתובת הבסיסית
@RequiredArgsConstructor

public class UsersController {

    private final UsersService usersService;
    private final VehiclesService vehiclesService;


    @GetMapping("/getAllUsers")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam long id) {
        Optional<Users> user = usersService.getUserById(id);
        if (user.isPresent()) {
            return "ברוך הבא " + user.get().getFullName() + "! הגעת בהצלחה.";
        } else {
            return "שגיאה: לא נמצא משתמש עם מספר מזהה זה.";
        }
    }
//    @PostMapping("/register")
//    public String registerGuest(@RequestBody Users newUser, @RequestParam String vehicleId,
//                                @RequestParam String vehicleType, // "HANDICAPPED", "TRUCK", "PRIVATE"
//                                @RequestParam String licensePlate) {
//        // 1. יצירת המשתמש החדש עם בונוס הרשמה
//        newUser.setStars(50);
//        Users savedUser = usersService.saveUser(newUser);
//
//        // 2. שיוך הרכב של האורח למשתמש החדש
//        // אנחנו קוראים ל-VehiclesService שיעדכן את הבעלים של הרכב
//        vehiclesService.assignVehicleToUser(vehicleId, savedUser.getUserId(),vehicleType,licensePlate);
//
//        return "ברוך הבא! נרשמת בהצלחה. הרכב " + vehicleId + " שויך לחשבונך ותקבל הנחה אוטומטית בפעם הבאה.";
//    }
    /**
     * בדיקת יתרת כוכבים/נקודות של משתמש
     */
    @PostMapping("/register")
    public String registerUser(@RequestBody Users newUser) {
        try {
            // נרשם את המשתמש ונותנים לו 50 כוכבים בהתחלה
            Users savedUser = usersService.addNewUser(newUser);
            return "המשתמש " + savedUser.getFullName() + " נרשם בהצלחה.";
        } catch (RuntimeException e) {
            return e.getMessage();  // אם קיים כבר משתמש עם אותו טלפון, תחזור שגיאה
        }
    }

    @GetMapping("/{userId}/points")
    public String getUserPoints(@PathVariable long userId) {
        return usersService.getUserById(userId)
                .map(u -> "User has " + u.getStars() + " stars.")
                .orElse("User not found.");
    }
}
