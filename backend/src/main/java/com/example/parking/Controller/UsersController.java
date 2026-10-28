package com.example.parking.Controller;

import com.example.parking.Entities.Users;
import com.example.parking.Service.UsersService;
import com.example.parking.Service.VehiclesService;
import com.example.parking.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final VehiclesService vehiclesService;
    private final JwtUtil jwtUtil;

    @GetMapping("/getAllUsers")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        try {
            // 1. חילוץ ה-id והסיסמה שהגיעו מה-React
            String idStr = loginRequest.get("userId");
            Long id = Long.parseLong(idStr);
            String phoneNumber = loginRequest.get("phoneNumber");

            if (idStr == null || phoneNumber == null) {
                return ResponseEntity.status(400).body("שגיאה: יש לספק גם מספר מזהה וגם מספר טלפון.");
            }
            // 2. חיפוש המשתמש לפי ה-Service המקורי שלך
            Optional<Users> userOptional = usersService.getUserById(id);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(401).body("שגיאה: לא נמצא משתמש עם מספר מזהה זה.");
            }

            Users user = userOptional.get();

            if (!user.getPhoneNumber().equals(phoneNumber)) {
                // אם הטלפון לא מתאים - זה אומר שמישהו מנסה לשקר או לנחש! חוסמים אותו מיד.
                return ResponseEntity.status(401).body("שגיאה: מספר הטלפון אינו תואם למשתמש זה.");
            }

            // 3. יצירת הטוקן: משתמשים ב-phoneNumber במקום email כי זה מה שיש באנטיטי שלך
            String token = jwtUtil.generateToken(user.getPhoneNumber(), "no_password", user.getUserId(),user.getRole());
            // 4. החזרת הטוקן ל-React
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user" ,user,
                    "message", "ברוך הבא " + user.getFullName() + "! הגעת בהצלחה."
            ));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("שגיאה: מספר מזהה לא תקין.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("שגיאה בתהליך ההתחברות: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users newUser) {
        try {
            // 1. הפעלת ה-Service המקורי שלך לשמירת המשתמש
            Users savedUser = usersService.addNewUser(newUser);

            // 2. יצירת טוקן עם ה-phoneNumber של המשתמש שנשמר (בלי getPassword שלא קיים אצלך)
            String token = jwtUtil.generateToken(savedUser.getPhoneNumber(), "no_password", savedUser.getUserId(),savedUser.getRole());

            // 3. החזרת תשובה ל-React
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", savedUser ,
                    "message", "המשתמש " + savedUser.getFullName() + " נרשם וחובר בהצלחה!"
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * בדיקת יתרת כוכבים/נקודות של משתמש
     */
    @GetMapping("/{userId}/points")
    public String getUserPoints(@PathVariable long userId) {
        return usersService.getUserById(userId)
                .map(u -> "User has " + u.getStars() + " stars.")
                .orElse("User not found.");
    }
    @GetMapping("/getById/{userId}")
    public Optional<Users> getUserById(@PathVariable long userId) {
        return usersService.getUserById(userId);
    }
}