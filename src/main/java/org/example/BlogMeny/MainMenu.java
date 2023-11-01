package org.example.BlogMeny;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.User;
import org.example.input.InputHandler;
import org.example.userLoggedIn.LoginMenu;

public class MainMenu {
    private final DatabaseFacade dbConnection;
    private final InputHandler inputHandler;
    private User loggedInUser;

    public MainMenu(DatabaseFacade dbConnection, InputHandler inputHandler) {
        this.dbConnection = dbConnection;
        this.inputHandler = inputHandler;
        loggedInUser = null;
    }
public void startMainMenu(){
    meny();
    inputHandler.closeScanner();
}
    private boolean meny() {
        while (true) {
            System.out.println("1. Create user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = inputHandler.getIntInput();

            switch (choice) {
                case 1:
                    inputHandler.getStringInput(); // Consume the newline character
                    Long createdUser = createUser();
                    break;
                case 2:
                    inputHandler.getStringInput(); // Consume the newline character
                    loggedInUser = login(); // Store the logged-in user

                    if (loggedInUser == null) {
                        // Login was unsuccessful, so continue the loop to show the main menu again.
                        System.out.println("Returning to the main menu...");
                    } else {
                        // Login was successful, proceed to the login menu.
                        LoginMenu loginMenu = new LoginMenu(loggedInUser); // Pass the logged-in user
                        loginMenu.startLoginMenu();
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
        String username = inputHandler.getStringInput();
        System.out.println("Enter password: ");
        String password = inputHandler.getStringInput();
        User user = new User(username, password);

        return dbConnection.createUser(user);
    }

    public User login() {
        System.out.println("Enter username: ");
        String username = inputHandler.getStringInput();
        System.out.println("Enter password: ");
        String password = inputHandler.getStringInput();
        User loginUser = new User(username, password);
        loggedInUser = dbConnection.login(loginUser); // Update the instance variable

        if (loggedInUser == null) {
            return null; // Return null to indicate unsuccessful login
        } else {
            return loggedInUser;
        }
    }



    // Add a getter to access the logged-in user from outside the class
    public User getLoggedInUser() {
        if (loggedInUser == null) {
            System.out.println("No user is logged in");
        } else {
            System.out.println("Logged in as: " + loggedInUser.getUsername());
        }
        return loggedInUser;
    }
}
