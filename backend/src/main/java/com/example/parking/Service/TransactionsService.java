package com.example.parking.Service;
import com.example.parking.Entities.Parking;
import com.example.parking.Entities.Transactions;
import com.example.parking.Entities.Vehicles;
import com.example.parking.Entities.enums.TransactionStatus;
import com.example.parking.Entities.enums.VehicleType;
import com.example.parking.Exceptions.ParkingException;
import com.example.parking.Reposetories.TransactionsRepo;
import com.example.parking.Reposetories.VehiclesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service

public class TransactionsService {

    // הזרקת הרפוזיטורי של העסקאות
    private final TransactionsRepo transactionsRepo;
    private final ParkingService parkingService;
    private final VehiclesRepo vehiclesRepo;

    /**
     * מחזירה את כל היסטוריית העסקאות במערכת (למנהל החניון)
     */
    public List<Transactions> getAllTransactions() {
        return transactionsRepo.findAll();
    }

    /**
     * מחזירה את כל העסקאות של רכב ספציפי
     */
    public List<Transactions> getTransactionsByVehicle(Vehicles vehicle) {
        return transactionsRepo.findByVehicle(vehicle);
    }
    /**
     * מוצאת עסקה ספציפית לפי ה-ID שלה
     */
    public Optional<Transactions> getTransactionById(long id) {
        return transactionsRepo.findById(id);
    }

    /**
     * פונקציה למחיקת היסטוריה (אם צריך)
     */
    public void deleteTransaction(long id) {
        transactionsRepo.deleteById(id);
    }

    /**
     * מחזירה את כל העסקאות שעדיין "פתוחות" (רכבים שחונים כרגע)
     */
    public List<Transactions> getActiveTransactions() {
        // אנחנו מחפשים את כל העסקאות שבהן endTime הוא עדיין 0
        // הערה: תצטרכי להוסיף את החתימה הזו ל-TransactionsRepo אם תרצי להשתמש בה
        return transactionsRepo.findAllByEndTimeIsNull();
    }
    public boolean existsByLicensePlateAndStatus(String licensePlate, TransactionStatus status) {
        return transactionsRepo.existsByLicensePlateAndStatus(licensePlate, status);
    }
    public Transactions createTransaction(String licensePlate) {
        Vehicles vehicle= vehiclesRepo.findByLicensePlate(licensePlate).
                orElseGet(()->{
                    Vehicles vehicleNew = new Vehicles();
                    vehicleNew.setLicensePlate(licensePlate);
                    vehicleNew.setVehicleType(VehicleType.REGULAR);
                    return vehiclesRepo.save(vehicleNew);
                });
        if (existsByLicensePlateAndStatus(licensePlate, TransactionStatus.ACTIVE)) {
            throw new ParkingException("הרכב כבר נמצא בחניון!");
        }
        Transactions transaction = new Transactions();
        transaction.setVehicle(vehicle);
        transaction.setLicensePlate(licensePlate);
        transaction.setUser(vehicle.getUser());

        return addTransaction(transaction);




    }
    public Transactions addTransaction(Transactions transactions) {
        Parking parking=parkingService.findOptimalSpot(transactions.getVehicle().getVehicleType());

        transactions.setParking(parking);
        transactions.setStatus(TransactionStatus.ACTIVE);
        transactions.setStartTime(LocalDateTime.now());
        return transactionsRepo.save(transactions);




    }
}