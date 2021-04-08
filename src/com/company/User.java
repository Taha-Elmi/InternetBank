package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private String firstName;
    private String lastName;
    private final String id;
    private String password;
    private ArrayList<Account> accountList;
    private boolean isAdmin;
    private boolean isLogged;

    public User(String firstName, String lastName, String id, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.isAdmin = false;
        this.isLogged = false;
    }

    public User(String firstName, String lastName, String id, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isLogged = false;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }

    public void deposit(Account account, int amount) {
        if (!accountList.contains(account)) {
            System.out.println("This account does not belong to you!");
            return;
        }
        int current = account.getBalance();
        current += amount;
        account.setBalance(current);
        System.out.println("Completed.");

        account.addTransaction(new Transaction(amount));
    }

    public void withdrawal(Account account, int amount) {
        if (!accountList.contains(account)) {
            System.out.println("This account does not belong to you!");
            return;
        }
        int current = account.getBalance();
        current -= amount;
        if (current >= 0) {
            account.setBalance(current);
            account.addTransaction(new Transaction(-amount));
            System.out.println("Completed.");
        }
        else
            System.out.println("Not enough money.");
    }

    public void transfer(Account srcAccount, Account destAccount, int amount) {
        if (!accountList.contains(srcAccount)) {
            System.out.println("This account does not belong to you!");
            return;
        }
        int current = srcAccount.getBalance();
        current -= amount;
        if (current >= 0) {
            srcAccount.setBalance(current);
            srcAccount.addTransaction(new Transaction(-amount));
            destAccount.setBalance(destAccount.getBalance() + amount);
            destAccount.addTransaction(new Transaction(amount));
            System.out.println("Completed.");
        } else
            System.out.println("Destination account doesn’t exist or there is not enough money in your account.");
    }

    public void checkBalance(Account account) {
        if (!accountList.contains(account)) {
            System.out.println("This account does not belong to you!");
            return;
        }
        System.out.println("The balance is: " + account.getBalance());
    }

    public void printAllAvailableAccounts() {
        Iterator<Account> iterator = accountList.iterator();
        int accountNum = 1;
        while (iterator.hasNext()) {
            System.out.print("Account " + accountNum + ": ");
            iterator.next().printAccountData();
            accountNum++;
        }
    }

    public void printUserData() {
        System.out.println("First Name: " + firstName + " , Last Name: " + lastName + " , ID: " + id);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public int numberOfAccounts() {
        return accountList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean getLogged() {
        return isLogged;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Account getAccount(int index) {
        index--;
        return accountList.get(index);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
