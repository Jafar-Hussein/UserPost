package org.example.userLoggedIn;

import org.example.Database.DatabaseFacade;
import org.example.accountContent.Post;
import org.example.accountContent.User;
import org.example.input.InputHandler;

import java.util.List;

public class LoginMenu {
protected DatabaseFacade databaseFacade;
protected InputHandler inputHandler;
protected User user;
protected Post post;
public LoginMenu(User loggedInUser) {
    databaseFacade = new DatabaseFacade();
    inputHandler = new InputHandler();
    user = loggedInUser;
    post = new Post();
}
public void startLoginMenu(){
    logInMeny();
    inputHandler.closeScanner();
}
private boolean logInMeny(){
    do {
        try {
            menyOptions();
            int choice = inputHandler.getIntInput();
            userChoice(choice);
        }catch (Exception e){
            System.out.println(e.getMessage());
            inputHandler.getStringInput();
        }
    }
    while (true);
}

    private void userChoice(int choice) {
    switch (choice){
        case 1:
            inputHandler.getStringInput();
            createPost();
            break;
        case 2:
            inputHandler.getStringInput();
            deletePost();
            break;
        case 3:
            inputHandler.getStringInput();
            updatePost();
            break;
            case 4:
                inputHandler.getStringInput();
                printPosts();
                break;
        case 5:
            System.out.println("Exiting...");
            System.exit(0);
        default:
            System.out.println("Invalid choice");
    }


    }

    public void printPosts() {
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


    public void updatePost() {
        System.out.println("Enter id of post to update");
        Long postId = inputHandler.getLongInput();
        inputHandler.getStringInput(); // Consume the newline character
        System.out.println("Enter new title: ");
        String postTitle = inputHandler.getStringInput();
        System.out.println("Enter new content: ");
        String postContent = inputHandler.getStringInput();
        Post post = new Post();
        post.setId(postId);
        post.setTitle(postTitle);
        post.setContent(postContent);
        databaseFacade.updatePost(post);
    }

   public void deletePost() {
        System.out.println("Enter id of post to delete: ");
        Long id = inputHandler.getLongInput();
        Post post = new Post();
        post.setId(id);
        databaseFacade.deletePost(post);
    }

    public boolean createPost() {
        // Check if the user's ID is null
        if (user.getId() == null) {
            System.out.println("User ID is not set. Please log in.");
            return false;
        }

        System.out.println("Enter title: ");
        String title = inputHandler.getStringInput();
        System.out.println("Enter content: ");
        String content = inputHandler.getStringInput();

        if (title.isEmpty() || content.isEmpty()) {
            System.out.println("Title and content cannot be empty.");
            return false;
        }

        post.setTitle(title);
        post.setContent(content);
        post.setUserId(user.getId());
        databaseFacade.createPost(post);
         return true;
    }



    private void menyOptions() {
        String[] menyOptions = {"1. Create post", "2. Delete post", "3. Update post","4. print posts ", "5. Exit"};
        for (String menyOption : menyOptions) {
            System.out.println(menyOption);
        }
    }
}
