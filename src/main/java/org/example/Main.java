package org.example;


import org.example.BlogMeny.MainMenu;
import org.example.Database.DatabaseFacade;
import org.example.input.InputHandler;

public class Main {

    public static void main(String[] args) {
        DatabaseFacade dbConnection = new DatabaseFacade();
        InputHandler inputHandler = new InputHandler();
       MainMenu mainMenu = new MainMenu(dbConnection, inputHandler);
       mainMenu.startMainMenu();


    }
}