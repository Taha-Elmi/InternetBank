package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Account {
    private UUID serial;
    private String id;
    private String firstName;
    private String lastName;
    private String type;
    private int balance;
    private ArrayList<Transaction> transactions;

    public Account(String id, String firstName, String lastName, String type, int balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.balance = balance;
        this.serial = UUID.randomUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    public Account(String id, String firstName, String lastName, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.balance = 0;
        this.serial = UUID.randomUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printTransactions() {
        Iterator<Transaction> it = transactions.iterator();
        while (it.hasNext()) {
            it.next().print();
        }
    }

    public void printAccountData() {
        System.out.println("Serial: " + serial.toString() + " , Type: " + type + " , Balance: " + balance);
    }

    public UUID getSerial() {
        return serial;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
