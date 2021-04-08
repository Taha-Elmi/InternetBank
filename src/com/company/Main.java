package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException{

        BankingSystem bankingSystem = new BankingSystem();
        User admin = new User("Taha", "Elmi", "sysadmin", "1234", true);
        bankingSystem.addUser(admin);

        UI display = new UI();
        while (!display.getStatus().equals("exit"))
            display.menuLoop(bankingSystem);
    }

}
