package org.example.BlogMeny;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.User;
import org.example.userLoggedIn.LoginMenu;

import java.util.Scanner;

public class MainMenu {
    private final DatabaseFacade dbConnection;
    private final Scanner scanner;

    public MainMenu(){
        dbConnection = new DatabaseFacade();
        scanner = new Scanner(System.in);
    }
    public boolean meny(){
        while (true) {
            System.out.println("1. Create user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    Long createdUser = createUser();
                    if (createdUser != null) {
                        System.out.println("User created successfully.");
                    }
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    User user = login();
                    if (user != null) {
                        // Pass the authenticated user to the PostMenu class
                        LoginMenu postMenu = new LoginMenu();
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public Long createUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        User user = new User(username, password);
        return dbConnection.createUser(user);
    }

    public User login() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        User loginUser = new User(username, password); // Create a User with username and password
        return dbConnection.login(loginUser); // Check if the login is successful
    }
}

