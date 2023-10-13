package org.example;


import org.example.Database.DatabaseConnection;
import org.example.accountContent.Post;
import org.example.accountContent.User;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        User user1 = new User();
        user1.setUsername("Jafar");
        user1.setPassword("Jafar_Hussein332");

//      Long userId =  dbConnection.createUser(user1);

        Post post1 = new Post();
        post1.setTitle("My first post");
        post1.setContent("This is my first post");
//        post1.setUserId(userId);
//        dbConnection.createPost(post1);

        User authenticatedUser = dbConnection.login(user1);

        if (authenticatedUser != null) {
            System.out.printf("Welcome %s", authenticatedUser.getUsername());
        } else {
            System.out.println("Failed to login");
        }




    }

    }
