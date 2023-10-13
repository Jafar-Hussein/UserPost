package org.example.Database;

import org.example.accountContent.Post;
import org.example.accountContent.User;

public class DatabaseFacade {
    private DatabaseConnection databaseConnection;
    public DatabaseFacade(){
        databaseConnection = new DatabaseConnection();
    }

    public Long createUser(User user){

        return databaseConnection.createUser(user);
    }
    public User login(User user){
        return databaseConnection.login(user);
    }
    public void deleteUser(User user){
        databaseConnection.deleteUser(user);
    }
    public Long createPost(Post post){
       return databaseConnection.createPost(post);
    }
    public void deletePost(Post post){
        databaseConnection.deletePost(post);
    }
    public void updatePost(Post post){
        databaseConnection.updatePost(post);
    }

}
