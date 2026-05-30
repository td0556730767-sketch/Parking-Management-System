package com.example.parking.Reposetories;
import com.example.parking.Entities.Transactions;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Entities.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {

//    // מוצא את החניה הפעילה של רכב מסוים (לפי לוחית רישוי וזמן סיום שהוא 0 או null)
//    // זה קריטי לרגע שהרכב לוחץ על "סיום חניה"
//    Optional<TransactionsRepo> findByLicensePlateAndEndTime(String licensePlate, long endTime);
//
//    // מציאת היסטוריית חניות של רכב מסוים
//    List<TransactionsRepo> findByLicensePlate(String licensePlate);
//
//    // בדיקה אם רכב נמצא כרגע בחניה (endTime == 0)
//    boolean existsByLicensePlateAndEndTime(String licensePlate, long endTime);

    // מוצא את כל העסקאות של רכב מסוים (להיסטוריה)
    List<Transactions> findByVehicle(Vehicles vehicle);

    // מוצא עסקה פעילה (כשה-endTime הוא 0)
    Optional<Transactions> findByVehicle_VehicleIdAndEndTime(long vehicleId, LocalDateTime endTime);

    // בודק אם רכב כבר חונה כרגע
    boolean existsByVehicle_VehicleIdAndEndTime(long vehicleId, LocalDateTime endTime);

    // מוצא את כל הרכבים שחונים כרגע בחניון
    List<Transactions> findAllByStatus(TransactionStatus status);
    boolean existsByLicensePlateAndStatus(String licensePlate, TransactionStatus status);
    Vehicles findByLicensePlate(final String licensePlate);
    List<Transactions> findAllByEndTimeIsNull();


}