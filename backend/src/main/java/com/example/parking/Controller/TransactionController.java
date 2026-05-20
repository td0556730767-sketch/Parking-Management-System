package com.example.parking.Controller;

import com.example.parking.Entities.Transactions;
import com.example.parking.Service.TransactionsService;
import com.example.parking.Service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions") // הכתובת הבסיסית
@RequiredArgsConstructor
public class TransactionController {
    final private TransactionsService transactionsService;
    final private VehiclesService vehiclesService;

    @GetMapping("/getAll")
    public List<Transactions> getAllTransactions() {
        return transactionsService.getAllTransactions();
    }
    @PostMapping("/reserve")
    public Transactions reserveTransaction(@RequestBody Transactions transactions) {
        return transactionsService.addTransaction(transactions);

    }
    @PostMapping("start/{licensePlate}")
    public Transactions startTransaction(@PathVariable String licensePlate) {
       return transactionsService.createTransaction(licensePlate);
    }

}
