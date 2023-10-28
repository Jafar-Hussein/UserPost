package org.example.BlogMeny;

import org.example.DatabaseFacade;
import org.example.accountContent.User;
import org.example.input.InputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;


class MainMenuTest {
    @Mock
    private DatabaseFacade mockDatabase;

    @Mock
    private InputHandler mockInputHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateUser() {
        MainMenu mainMenu = new MainMenu(mockDatabase, mockInputHandler);

        // Mock user input for creating a user
        Mockito.when(mockInputHandler.getStringInput())
                .thenReturn("testUsername")
                .thenReturn("testPassword");

        // Mock the database facade response
        Mockito.when(mockDatabase.createUser(Mockito.any(User.class)))
                .thenReturn(1L);

        Long userId = mainMenu.createUser();
        assertNotNull(userId);
        // You may add more assertions to check the behavior of createUser()
    }

    @Test
    public void testLoginSuccessful() {
        MainMenu mainMenu = new MainMenu(mockDatabase, mockInputHandler);

        // Create a mock User object that should match the one returned from the database
        User expectedUser = new User("validUsername", "validPassword");

        // Configure the mockDatabase to return the expectedUser when login is called with any User object
        Mockito.when(mockDatabase.login(Mockito.any(User.class)))
                .thenReturn(expectedUser);

        // Mock user input for login
        Mockito.when(mockInputHandler.getStringInput())
                .thenReturn("validUsername")
                .thenReturn("validPassword");

        mainMenu.login();
        assertEquals(expectedUser, mainMenu.getLoggedInUser());

    }


    @Test
    public void testLoginUnsuccessful() {
        MainMenu mainMenu = new MainMenu(mockDatabase, mockInputHandler);

        // Mock user input for login
        Mockito.when(mockInputHandler.getStringInput())
                .thenReturn("invalidUsername")
                .thenReturn("invalidPassword");

        // Mock the database facade response for an unsuccessful login
        Mockito.when(mockDatabase.login(Mockito.any(User.class)))
                .thenReturn(null);

        mainMenu.login();
        assertNull(mainMenu.getLoggedInUser());
    }
}