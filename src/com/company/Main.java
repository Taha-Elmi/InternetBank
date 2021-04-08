package com.company;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException{

        BankingSystem bankingSystem = new BankingSystem();
        User admin = new User("Taha", "Elmi", "sysadmin", "1234", true);
        UI display = new UI();
        while (!display.getStatus().equals("exit"))
            display.menuLoop(bankingSystem);
    }

}
