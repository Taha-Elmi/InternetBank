package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BankingSystem {
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public BankingSystem() {
        users = new ArrayList<User>();
        accounts = new ArrayList<Account>();
    }

    public boolean register(User user) {
        for (User iterator : users) {
            if (iterator.equals(user)) {
                System.out.println("There is already a user with this ID.");
                return false;
            }
        }
        return true;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean login(String id, String password) {
        User temp = null;
        boolean isFound = false;
        for (User user : users) {
            if (user.getId().equals(id)) {
                temp = user;
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            System.out.println("user doesn't exists or password is incorrect.");
            return false;
        }

        if (temp.getPassword().equals(password)) {
            System.out.println("Logged in.");
            temp.setLogged(true);
            return true;
        } else {
            System.out.println("user doesn't exists or password is incorrect.");
            return false;
        }
    }

    public boolean adminLogin(String id, String password) {
        User temp = null;
        boolean isFound = false;
        for (User user : users) {
            if (user.getId().equals(id)) {
                temp = user;
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            System.out.println("Username or password is incorrect.");
            return false;
        }

        if (temp.getPassword().equals(password) && temp.isAdmin()) {
            System.out.println("Logged in as sysadmin.");
            temp.setLogged(true);
            return true;
        } else {
            System.out.println("Username or password is incorrect.");
            return false;
        }
    }

    public void removeUser(User user) {
        if (!users.contains(user)) {
            System.out.println("User doesn’t exist.");
            return;
        }

        users.remove(user);
        System.out.println("User removed.");
    }

    public void displayUsers() {
        int userNum = 1;
        for (User user : users) {
            System.out.print("User " + userNum + ":  ");
            user.printUserData();
            userNum++;
        }
    }

    public boolean addAccount(Account account) {
        for (Account iterator : accounts) {
            if (iterator.equals(account)) {
                System.out.println("There is already an account with this ID.");
                return false;
            }
        }
        accounts.add(account);
        System.out.println("New account opened.");
        return true;
    }

    public void removeAccount(Account account) {
        if (accounts.remove(account))
            System.out.println("Account removed.");
        else
            System.out.println("Account doesn’t exist.");
    }

    public void displayAccounts() {
        int accountNum = 1;
        for (Account account : accounts) {
            System.out.print("Account " + accountNum + ":  ");
            account.printAccountData();
            accountNum++;
        }
    }

    public Account findAccount(String serial) {
        for (Account account : accounts) {
            if (account.getSerial().toString().equals(serial))
                return account;
        }
        return null;
    }

    public User findUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }

    public User findLoggedUser() {
        for (User user : users) {
            if (user.getLogged())
                return user;
        }
        return null;
    }
}
