package com.company;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UI {
    private String status;

    public UI() {
        status = "main menu";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void menu() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("1.Sign up");
        System.out.println("2.Log in");
        System.out.println("3.System Admin");
        System.out.println("4.Exit");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
               status = "sign up";
               break;
            case 2:
                status = "log in";
                break;
            case 3:
                status = "system admin log in";
                break;
            case 4:
                status = "exit";
                break;
            default:
                System.out.println("Invalid input");
                TimeUnit.SECONDS.sleep(2);
        }
    }

    public void signUp(BankingSystem bankingSystem) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Info");
        System.out.print("First Name: ");
        String firstName = input.next();

        System.out.print("Last Name: ");
        String lastName = input.next();

        System.out.print("ID: ");
        String id = input.next();

        System.out.print("Password: ");
        String password = input.next();

        User user = new User(firstName, lastName, id, password);
        if (bankingSystem.register(user)) {
            bankingSystem.addUser(user);
            System.out.println("user created.");
        } else
            System.out.println("user already exists.");

        TimeUnit.SECONDS.sleep(2);
        status = "main menu";
    }

    public void logIn(BankingSystem bankingSystem) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter user ID and password");
        System.out.print("ID: ");
        String id = input.next();
        System.out.print("Password: ");
        String password = input.next();
        bankingSystem.login(id, password);

        TimeUnit.SECONDS.sleep(2);
        status = "user menu";
    }

    public void userMenu(BankingSystem bankingSystem) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("1.Existing accounts");
        System.out.println("2.Add new account");
        System.out.println("3.Log out");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                status = "account menu";
                accountMenu(bankingSystem, existingAccounts(bankingSystem));
                break;
            case 2:
                status = "add new account";
                break;
            case 3:
                User user = bankingSystem.findLoggedUser();
                user.setLogged(false);
                status = "main menu";
                break;
            default:
                System.out.println("Invalid input.");
                TimeUnit.SECONDS.sleep(2);
        }
    }

    public Account existingAccounts(BankingSystem bankingSystem) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        User user = bankingSystem.findLoggedUser();
        user.printAllAvailableAccounts();

        Scanner input = new Scanner(System.in);
        int accountNum = input.nextInt();
        if (accountNum < 1 || accountNum > user.numberOfAccounts()) {
            System.out.println("Invalid input");
            TimeUnit.SECONDS.sleep(2);
            return existingAccounts(bankingSystem);
        }
        return user.getAccount(accountNum);
    }

    public void accountMenu(BankingSystem bankingSystem, Account account) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Logged into account.\n" +
                "1.Withdrawal\n" +
                "2.Deposit\n" +
                "3.Transfer\n" +
                "4.Check balance\n" +
                "5.Back");
        User user = bankingSystem.findLoggedUser();
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter the amount: ");
                int amountOfWithdrawal = input.nextInt();
                user.withdrawal(account, amountOfWithdrawal);
                TimeUnit.SECONDS.sleep(2);
                break;
            case 2:
                System.out.print("Enter the amount: ");
                int amountOfDeposit = input.nextInt();
                user.deposit(account, amountOfDeposit);
                TimeUnit.SECONDS.sleep(2);
                break;
            case 3:
                System.out.print("Enter destination account's serial: ");
                String serial = input.next();
                Account destAccount = bankingSystem.findAccount(serial);
                if (destAccount == null) {
                    System.out.println("Destination account doesn’t exist or there is not enough money in your account.");
                    TimeUnit.SECONDS.sleep(2);
                    accountMenu(bankingSystem, account);
                    break;
                }
                System.out.print("Enter the amount: ");
                int amount = input.nextInt();
                user.transfer(account, destAccount, amount);
                TimeUnit.SECONDS.sleep(2);
                break;
            case 4:
                System.out.println(account.getBalance());
                TimeUnit.SECONDS.sleep(2);
                break;
            case 5:
                System.out.println("Logged out of account.");
                TimeUnit.SECONDS.sleep(2);
                setStatus("user menu");
                break;
            default:
                System.out.println("Invalid input");
                TimeUnit.SECONDS.sleep(2);
        }
    }

    public void addNewAccount(BankingSystem bankingSystem) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        User user = bankingSystem.findLoggedUser();
        System.out.print("Enter the ID: ");
        Scanner input = new Scanner(System.in);
        String id = input.next();
        System.out.print("Enter the account's type: ");
        String type = input.next();
        System.out.println("Do you wanna deposit an initial balance to your account?\n1.Yes\n2.No");
        int hasInit = input.nextInt();
        Account account;
        switch (hasInit) {
            case 1:
                System.out.print("Enter the initial amount: ");
                int initial = input.nextInt();
                account = new Account(id, user.getFirstName(), user.getLastName(), type, initial);
                break;
            case 2:
                account = new Account(id, user.getFirstName(), user.getLastName(), type);
                break;
            default:
                System.out.println("Invalid input.");
                TimeUnit.SECONDS.sleep(2);
                addNewAccount(bankingSystem);
                return;
        }
        if (bankingSystem.addAccount(account)) {
            user.addAccount(account);
            TimeUnit.SECONDS.sleep(2);
        }
        status = "user menu";
    }

    public void systemAdminLogin(BankingSystem bankingSystem) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.print("Enter ID: ");
        Scanner input = new Scanner(System.in);
        String id = input.next();
        System.out.print("Enter the password: ");
        String password = input.next();
        if (!bankingSystem.adminLogin(id, password)) {
            TimeUnit.SECONDS.sleep(2);
            systemAdminLogin(bankingSystem);
            return;
        }
        TimeUnit.SECONDS.sleep(2);
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("1.display users\n" +
                "2.display accounts\n" +
                "3.remove user\n" +
                "4.remove account\n" +
                "5.log out");

        status = "system admin menu";
    }

    public void systemAdminMenu(BankingSystem bankingSystem) throws IOException, InterruptedException{
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                bankingSystem.displayUsers();
                break;
            case 2:
                bankingSystem.displayAccounts();
                break;
            case 3:
                System.out.print("Enter user's ID: ");
                String id = input.next();
                User user = bankingSystem.findUser(id);
                if (user == null)
                    System.out.println("User doesn’t exist.");
                else
                    bankingSystem.removeUser(user);
                break;
            case 4:
                System.out.print("Enter account's serial: ");
                String serial = input.next();
                Account account = bankingSystem.findAccount(serial);
                if (account == null)
                    System.out.println("Account doesn’t exist.");
                else
                    bankingSystem.removeAccount(account);
                break;
            case 5:
                bankingSystem.findLoggedUser().setLogged(false);
                status = "main menu";
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    public void menuLoop(BankingSystem bankingSystem) throws IOException, InterruptedException{
        switch (status) {
            case "main menu":
                menu();
                break;
            case "sign up":
                signUp(bankingSystem);
                break;
            case "log in":
                logIn(bankingSystem);
                break;
            case "user menu":
                userMenu(bankingSystem);
                break;
            case "account menu":
                accountMenu(bankingSystem, existingAccounts(bankingSystem));
                break;
            case "add new account":
                addNewAccount(bankingSystem);
                break;
            case "system admin log in":
                systemAdminLogin(bankingSystem);
                break;
            case "system admin menu":
                systemAdminMenu(bankingSystem);
                break;
            case "exit":
                break;
            default:
                System.out.println("An error occurred");
        }
    }
}
