package org.example.userLoggedIn;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.Post;
import org.example.accountContent.User;
import org.example.input.InputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginMenuTest {

    private LoginMenu loginMenu;
    private DatabaseFacade mockDatabase;
    private InputHandler mockInputHandler;
    private User mockUser;
    private Post mockPost;

    @BeforeEach
    public void setUp() {
        mockDatabase = Mockito.mock(DatabaseFacade.class);
        mockInputHandler = Mockito.mock(InputHandler.class);
        mockUser = Mockito.mock(User.class);
        mockPost = Mockito.mock(Post.class);
        loginMenu = new LoginMenu(mockUser);
        loginMenu.databaseFacade = mockDatabase;
        loginMenu.inputHandler = mockInputHandler;
        loginMenu.user = mockUser;
        loginMenu.post = mockPost;
    }

    @Test
    public void testCreatePostSuccess() {
        when(mockUser.getId()).thenReturn(123L);
        when(mockInputHandler.getStringInput()).thenReturn("Test Title", "Test Content");

        loginMenu.createPost();

        // Verify that the createPost method of mockDatabase was called with the expected mockPost argument
        verify(mockDatabase).createPost(mockPost);
    }



    @Test
    public void testDeletePost() {
        when(mockInputHandler.getLongInput()).thenReturn(123L);

        loginMenu.deletePost();

        // Verify that the deletePost method of mockDatabase was called with the expected Post argument
        verify(mockDatabase).deletePost(argThat(post -> post.getId() == 123L)); // Lambda expression that checks if the id of the Post argument is 123L
    }

@Test
public void testUpdatePost() {
    when(mockInputHandler.getLongInput()).thenReturn(789L);
    when(mockInputHandler.getStringInput()).thenReturn("Updated Title", "Updated Content");

    loginMenu.updatePost();

    // Create an argument captor for the Post class
    ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

    // Verify that the updatePost method of mockDatabase was called with the expected Post argument
    verify(mockDatabase).updatePost(postCaptor.capture());

    // Retrieve the captured argument
    Post capturedPost = postCaptor.getValue();

    // Make assertions on the captured argument
    assertEquals(789L, capturedPost.getId());
    assertEquals("Updated Title", capturedPost.getTitle());
    assertEquals("Updated Content", capturedPost.getContent());
}

    @Test
    public void testPrintPosts() {
        when(mockUser.getId()).thenReturn(123L);
        List<Post> mockUserPosts = new ArrayList<>();
        mockUserPosts.add(new Post("Title 1", "Content 1", 123L));
        mockUserPosts.add(new Post("Title 2", "Content 2", 123L));
        when(mockDatabase.getUserPost(mockPost)).thenReturn(mockUserPosts);

        loginMenu.printPosts();

        // You can add assertions to check the printed output or other expected behavior.
    }
}