package com.company;

import java.util.Date;

public class Transaction {
    private int amount;
    private Date date;

    public Transaction(int amount) {
        this.amount = amount;
        this.date = new Date();
    }

    public void print() {
        System.out.println("The transaction was done on " + date.toString() + " with the amount of " + amount + ".");
    }
}
