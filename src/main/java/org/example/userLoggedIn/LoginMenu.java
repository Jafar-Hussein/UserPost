package org.example.userLoggedIn;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.Post;
import org.example.accountContent.User;

import java.util.Scanner;

public class LoginMenu {
private final DatabaseFacade databaseFacade;
private final Scanner scanner;
private final User user;
private final Post post;
public LoginMenu() {
    databaseFacade = new DatabaseFacade();
    scanner = new Scanner(System.in);
    user = new User();
    post = new Post();
}
public boolean loggInMeny(){
    boolean isRunning = true;
    while (isRunning) {
        menyOptions();
        int choice = scanner.nextInt();
        userChoice(choice);
    }
    return true;
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
    }

    private void updatePost() {

    }

    private void deletePost() {
        System.out.println("Enter id of post to delete: ");
        Long id = scanner.nextLong();
        Post post = new Post();
        post.setId(id);
        databaseFacade.deletePost(post);
    }

    private void createPost() {
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
