package org.example.userLoggedIn;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.Post;
import org.example.accountContent.User;

import java.util.List;
import java.util.Scanner;

public class LoginMenu {
private final DatabaseFacade databaseFacade;
private final Scanner scanner;
private final User user;
private final Post post;
public LoginMenu(User loggedInUser) {
    databaseFacade = new DatabaseFacade();
    scanner = new Scanner(System.in);
    user = loggedInUser;
    post = new Post();
}
public boolean logInMeny(){
    do {
        try {
            menyOptions();
            int choice = scanner.nextInt();
            userChoice(choice);
        }catch (Exception e){
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }
    while (true);
}

    private void userChoice(int choice) {
    switch (choice){
        case 1:
            scanner.nextLine();
            createPost();
            break;
        case 2:
            scanner.nextLine();
            deletePost();
            break;
        case 3:
            scanner.nextLine();
            updatePost();
            break;
            case 4:
                scanner.nextLine();
                printPosts();
                break;
        case 5:
            System.out.println("Exiting...");
            System.exit(0);
        default:
            System.out.println("Invalid choice");
    }


    }

    private void printPosts() {
        // print all of the user posts
        post.setUserId(user.getId());
        List<Post> userPosts = databaseFacade.getUserPost(post);

        if (userPosts != null && !userPosts.isEmpty()) {
            System.out.println("User's posts:");
            for (Post post : userPosts) {
                System.out.printf("Title: %s\n", post.getTitle());
                System.out.printf("Content: %s\n", post.getContent());
                System.out.println("-------------");
            }
        } else {
            System.out.println("No posts found for the user.");
        }
    }


    private void updatePost() {
        System.out.println("Enter id of post to update");
        Long postId = scanner.nextLong();
        System.out.println("Enter new title: ");
        String postTitle = scanner.nextLine();
        System.out.println("Enter new content: ");
        String postContent = scanner.nextLine();
        Post post = new Post();
        post.setId(postId);
        post.setTitle(postTitle);
        post.setContent(postContent);
        databaseFacade.updatePost(post);
    }

    private void deletePost() {
        System.out.println("Enter id of post to delete: ");
        Long id = scanner.nextLong();
        Post post = new Post();
        post.setId(id);
        databaseFacade.deletePost(post);
    }

    private void createPost() {
        // Check if the user's ID is null
        if (user.getId() == null) {
            System.out.println("User ID is not set. Please log in.");
            return;
        }

        System.out.println("Enter title: ");
        String title = scanner.nextLine();
        System.out.println("Enter content: ");
        String content = scanner.nextLine();

        post.setTitle(title);
        post.setContent(content);
        post.setUserId(user.getId());
        databaseFacade.createPost(post);
    }



    private void menyOptions() {
        String[] menyOptions = {"1. Create post", "2. Delete post", "3. Update post","4. print posts ", "5. Exit"};
        for (String menyOption : menyOptions) {
            System.out.println(menyOption);
        }
    }
}
