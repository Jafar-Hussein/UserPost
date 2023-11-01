package org.example.database;

import org.example.Database.DatabaseConnection;
import org.example.accountContent.Post;
import org.example.accountContent.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    private DatabaseConnection databaseConnection;
    @BeforeEach
    void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @Test
    void getConnection() {
// Create an instance of DatabaseConnection
        DatabaseConnection dbConnection = new DatabaseConnection();

        // Attempt to get a connection
        dbConnection.getConnection();

        // Check if the connection is not null
        assertNotNull(dbConnection.getConnection());
    }

    @Test
    void createUser() {
        User user = new User("Jafar", "Jafar_Hussein332");

        // Create the user
        Long userId = databaseConnection.createUser(user);

        // Check if the user was created
        assertNotNull(userId);

        // Clean up: Delete the user you created during the test
        User userToDelete = new User();
        userToDelete.setId(userId);
        databaseConnection.deleteUser(userToDelete);
    }

    @Test
    void login() {
        User loginUser = new User("Jafar", "Jafar_Hussein332");

        databaseConnection.login(loginUser);

        assertNotNull(databaseConnection.login(loginUser));
    }

    @Test
    void createPost() {
        // Create a post
        Post post = new Post("My first post", "This is my first post", 1L);


        // Check if the post was created
        assertNotNull(databaseConnection.createPost(post));

        Post postToDelete = new Post();
        postToDelete.setId(post.getId());
        // Clean up: Delete the post you created during the test
        databaseConnection.deletePost(postToDelete);
    }

    @Test
    void getUserPost() {
        Post userPost = new Post();
        userPost.setUserId(1L);

        // Get the user's posts
        List<Post> posts = databaseConnection.getUserPost(userPost);

        // Check if posts were retrieved
        assertNotNull(posts);

        // Clean up: You don't need to clean up in this case
    }

    @Test
    void updatePost() {
        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle("My updated post");
        updatedPost.setContent("This is my updated post");

        databaseConnection.updatePost(updatedPost);

        assertNotNull(databaseConnection.updatePost(updatedPost));
        assertTrue(databaseConnection.updatePost(updatedPost));
    }

    @Test
    void deletePost() {
        Post updatedPost = new Post();
        updatedPost.setId(1L);

        databaseConnection.deletePost(updatedPost);

        assertNotNull(databaseConnection.deletePost(updatedPost));
        assertTrue(databaseConnection.deletePost(updatedPost));
    }
}