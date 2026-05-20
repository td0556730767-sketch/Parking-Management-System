package com.example.parking.Entities.enums;

public enum TransactionStatus {
    RESERVED,  // החניה הוזמנה, הרכב עוד לא הגיע
    ACTIVE,    // הרכב חונה כרגע
    COMPLETED, // הרכב יצא והעסקה נסגרה
    CANCELLED  // ההזמנה בוטלה
}
