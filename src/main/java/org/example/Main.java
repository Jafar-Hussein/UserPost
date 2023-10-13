package org.example;

import org.example.Database.DatabaseFacade;

import java.util.Scanner;

public class Main {
    private static DatabaseFacade dbConnection;
    private static Scanner scanner = new Scanner(System.in);
    private static Long userId;

    public static void main(String[] args) {
        dbConnection = new DatabaseFacade();


    }
}