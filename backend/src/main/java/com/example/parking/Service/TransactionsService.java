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
    //מציאת כל הרכבים שנמצאים כרגע בחניה
    public List<Transactions> findTransactionsByStatus() {
        return transactionsRepo.findAllByStatus(TransactionStatus.ACTIVE);
    }


    public Transactions exitParking(String licensePlate) {
        Transactions activeTransaction = transactionsRepo.findAllByStatus(TransactionStatus.ACTIVE)
                .stream()
                .filter(t -> t.getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle is not currently in the parking lot"));

        LocalDateTime actualExitTime = LocalDateTime.now();

        double penalty = 0.0;

        if (activeTransaction.getEndTime() != null) {

            // אם השעה עכשיו (actualExitTime) היא אחרי זמן הסיום שהוגדר מראש (endTime) -> יש חריגה!
            if (actualExitTime.isAfter(activeTransaction.getEndTime())) {

                // מחשבים כמה דקות חריגה היו בין זמן הסיום המוזמן לזמן של עכשיו
                long minutesOverstayed = java.time.Duration.between(activeTransaction.getEndTime(), actualExitTime).toMinutes();

                // תעריף קנס חריגה: 50 ש"ח לשעה (שזה 0.83 ש"ח לדקה)
                double penaltyPerMinute = 50.0 / 60.0;
                penalty = minutesOverstayed * penaltyPerMinute;

                System.out.println("הרכב חרג ב-" + minutesOverstayed + " דקות. קנס: " + penalty);
            }
        }

        double pricePerHour = (activeTransaction.getUser() == null) ? 15.0 : 5.0;
        double pricePerMinute = pricePerHour / 60.0;

        long totalMinutesParked = java.time.Duration.between(activeTransaction.getStartTime(), actualExitTime).toMinutes();
        double basePrice = totalMinutesParked * pricePerMinute;
        activeTransaction.setEndTime(actualExitTime);
        activeTransaction.setTotalPayment(basePrice + penalty);
        activeTransaction.setStatus(TransactionStatus.COMPLETED);

        return transactionsRepo.save(activeTransaction);


    }


}