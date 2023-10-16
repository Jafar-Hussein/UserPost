package org.example.BlogMeny;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.User;
import org.example.userLoggedIn.LoginMenu;

import java.util.Scanner;

public class MainMenu {
    private final DatabaseFacade dbConnection;
    private final Scanner scanner;
    private User loggedInUser; // Add a field to store the logged-in user

    public MainMenu() {
        dbConnection = new DatabaseFacade();
        scanner = new Scanner(System.in);
        loggedInUser = null; // Initialize logged-in user to null
    }

    public boolean meny() {
        while (true) {
            System.out.println("1. Create user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    Long createdUser = createUser();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    loggedInUser = login(); // Store the logged-in user

                    if (loggedInUser == null) {
                        // Login was unsuccessful, so continue the loop to show the main menu again.
                        System.out.println("Returning to the main menu...");
                    } else {
                        // Login was successful, proceed to the login menu.
                        LoginMenu loginMenu = new LoginMenu(loggedInUser); // Pass the logged-in user
                        loginMenu.logInMeny();
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
        User loginUser = new User(username, password);
        User loggedInUser = dbConnection.login(loginUser);

        if (loggedInUser == null) {
            return null; // Return null to indicate unsuccessful login
        } else {
            return loggedInUser;
        }
    }


    // Add a getter to access the logged-in user from outside the class
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
