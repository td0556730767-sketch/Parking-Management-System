package com.example.parking.Reposetories;
import com.example.parking.Entities.Parking;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Entities.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParkingRepo extends JpaRepository<Parking, Long> {

    // 1. למצוא את כל המקומות הפנויים כרגע
    List<Parking> findByIsOccupiedFalse();
    //List<Parking> findByIsOccupiedTrue();

    // 2. למצוא מקומות פנויים לפי סוג (למשל: רק חניית נכים פנויה)
    List<Parking> findBySpotTypeAndIsOccupiedFalse(VehicleType spotType);


    // 3. למצוא מקומות שהמחיר לשעה שלהם נמוך מסכום מסוים (לסינון חניות זולות)
    List<Parking> findByPricePerHourLessThanEqual(double price);

    // 4. לבדוק אם מקום ספציפי פנוי לפני שמתחילים חניה
    boolean existsBySpotIdAndIsOccupiedFalse(long spotId);

    // 5. למצוא את כל המקומות במיקום מסוים (למשל: כל החניות בקומה 1)
    List<Parking> findByLocationX(Integer floor);
    //List<Parking>findAllByIsOccupiedTrue();


}