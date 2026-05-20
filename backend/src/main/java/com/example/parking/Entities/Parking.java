package com.example.parking.Entities;


import com.example.parking.Entities.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Entity

public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long spotId;          // מזהה ייחודי לחניה
    private int locationX;
    private int locationY;// מיקום החניה (למשל קומה 1, מספר 5)
    private boolean isOccupied;   // אם החניה תפוסה או לא
    private long nextAvailableTime; // הזמן הבא שהחניה תהיה פנויה (באורך זמן בשניות/תאריך ושעה)
    private double pricePerHour;  // מחיר לשעה בחניה
   @Enumerated(EnumType.STRING)
    private VehicleType spotType;    // למשל: "נכים", "חשמלי", "רגיל"
   //  אין צורך בגטרים וסטרים הודות ל-Lombok
}
