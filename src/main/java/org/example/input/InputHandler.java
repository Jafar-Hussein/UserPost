package org.example.input;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;


    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public String getStringInput(){
        return scanner.nextLine();
    }

    public int getIntInput(){
        // make sure the input is an integer
        int input = 0; // Initialize to 0
        boolean validInput = true; // Initialize value to true
        while (validInput) { // Loop until valid input is entered
            try {
                input = scanner.nextInt(); // Try to read an integer
                validInput = false; // If no exception is thrown, the input is valid
            } catch (NumberFormatException e) { // Catch the exception if the input is not an integer
                System.out.println("Invalid input, please enter a number");
            }
        }
        // Consume the newline character
        return input;
    }

    public Long getLongInput(){
        // make sure the input is Long
        Long input = 0L; // Initialize to 0
        boolean validInput = true; // Initialize value to true
        while (validInput) { // Loop until valid input is entered
            try {
                input = scanner.nextLong(); // Try to read a Long
                validInput = false; // If no exception is thrown, the input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
        // Consume the newline character
        return input;
    }

    public void closeScanner(){
        scanner.close();
    }
}
