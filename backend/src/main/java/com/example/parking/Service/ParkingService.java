package com.example.parking.Service;

import com.example.parking.Entities.Parking;
import com.example.parking.Entities.Transactions;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Entities.enums.VehicleType;
import com.example.parking.Exceptions.ParkingException;
import com.example.parking.Reposetories.ParkingRepo;
import com.example.parking.Reposetories.TransactionsRepo;
import com.example.parking.Reposetories.UsersRepo;
import com.example.parking.Reposetories.VehiclesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // חוסך את כתיבת הקונסטרוקטור ידנית
public class ParkingService {

    private final ParkingRepo parkingRepo;
    private final TransactionsRepo transactionsRepo;
    private final VehiclesRepo vehiclesRepo;
    private final UsersRepo usersRepo;

    public List<Parking> getAllParkings() {
        return parkingRepo.findAll();
    }

    public List<Parking> getAllParkingAvailable() {
        return parkingRepo.findByIsOccupiedFalse();
    }

    /**
     * כניסה לחניון
     */
//    public String startParking(String licensePlate) {
//        // 1. בדיקה אם כבר יש חניה פעילה לרכב זה
//        if (transactionsRepo.existsByLicensePlateAndStatus(licensePlate, "ACTIVE")) {
//            return "שגיאה: הרכב כבר נמצא בחניון.";
//        }
//
//        // 2. חיפוש מקום פנוי
//        List<Parking> availableSpots = parkingRepo.findByIsOccupiedFalse();
//        if (availableSpots.isEmpty()) {
//            return "שגיאה: אין מקומות פנויים כרגע.";
//        }
//        Parking spot = availableSpots.get(0);
//
//        // 3. בדיקה האם הרכב רשום (מנוי)
//        Optional<Vehicles> registeredVehicle = vehiclesRepo.findByLicensePlate(licensePlate);
//
//        // בניית העסקה בעזרת Builder (נקי יותר)
//        Transactions.TransactionsBuilder txBuilder = Transactions.builder()
//                .licensePlate(licensePlate)
//                .spotId(spot.getSpotId())
//                .startTime(LocalDateTime.now())
//                .status("ACTIVE")
//                .paymentStatus(false);
//
//        // אם המנוי קיים, נשייך אותו ואת בעליו
//        if (registeredVehicle.isPresent()) {
//            txBuilder.vehicle(registeredVehicle.get());
//            txBuilder.user(registeredVehicle.get().getUser());
//        }
//
//        transactionsRepo.save(txBuilder.build());
//
//        // 4. עדכון החניה לתפוסה
//        spot.setOccupied(true);
//        parkingRepo.save(spot);
//
//        String message = registeredVehicle.isPresent() ? "שלום ללקוח הרשום! " : "ברוך הבא אורח! ";
//        return message + "חניה התחילה במקום: " + spot.getLocation();
//    }

    /**
     * יציאה מהחניון
     */
//    public String endParking(String licensePlate) {
//        // 1. מציאת העסקה הפעילה לפי הלוחית והסטטוס
//        Optional<Transactions> activeTx = transactionsRepo.findByLicensePlateAndStatus(licensePlate, "ACTIVE");
//        if (activeTx.isEmpty()) {
//            return "שגיאה: לא נמצאה חניה פעילה לרכב זה.";
//        }
//        Transactions transaction = activeTx.get();
//
//        // 2. חישוב זמן ומחיר
//        LocalDateTime endTime = LocalDateTime.now();
//        long minutes = Duration.between(transaction.getStartTime(), endTime).toMinutes();
//        double hours = Math.max(minutes / 60.0, 0.02); // מינימום לחיוב
//
//        Optional<Parking> spotOpt = parkingRepo.findById(transaction.getSpotId());
//        if (spotOpt.isEmpty()) return "שגיאה במציאת נתוני החניה.";
//        Parking spot = spotOpt.get();
//
//        double finalPrice = hours * spot.getPricePerHour();
//
//        // 3. בדיקת הטבות (נכה/מנוי)
//        if (transaction.getVehicle() != null && "HANDICAPPED".equals(transaction.getVehicle().getVehicleType())) {
//            finalPrice = 0; // חינם לנכים
//        } else if (transaction.getUser() != null) {
//            finalPrice = finalPrice * 0.9; // 10% הנחה למנוי
//        }
//
//        // 4. סגירת העסקה
//        transaction.setEndTime(endTime);
//        transaction.setTotalPayment(finalPrice);
//        transaction.setStatus("COMPLETED");
//        transaction.setPaymentStatus(true);
//        transactionsRepo.save(transaction);
//
//        // 5. שחרור החניה
//        spot.setOccupied(false);
//        parkingRepo.save(spot);
//
//        return String.format("החניה הסתיימה. מחיר סופי: %.2f ש\"ח.", finalPrice);
//    }

    public void addParking(Parking parking) {
        parkingRepo.save(parking);
    }

    public List<Parking> findByType(VehicleType type)
    {
        return parkingRepo.findBySpotTypeAndIsOccupiedFalse(type);
    }
    public Parking findOptimalSpot(VehicleType type)
    {
        List<Parking> availableParkings = parkingRepo.findBySpotTypeAndIsOccupiedFalse(type);
        if (availableParkings.isEmpty()&& type!=VehicleType.HANDICAPPED)
            throw new ParkingException("מצטערים אין מקום פנוי כרגע");
        if(availableParkings.isEmpty()&& type==VehicleType.HANDICAPPED){
            availableParkings=parkingRepo.findBySpotTypeAndIsOccupiedFalse(VehicleType.REGULAR);
        }
        if (availableParkings.isEmpty())
            throw new ParkingException("מצטערים אין מקום פנוי כרגע לנכה וגם לרגיל");
        return availableParkings.stream().min(
                Comparator.comparingDouble(spot-> Math.sqrt(Math.pow(spot.getLocationX(),2))+Math.pow(spot.getLocationY(),2))
        ).orElseThrow(()->new ParkingException("מצטערים איו חניה פנויה "));

    }
}