package org.example.Database;

import org.example.accountContent.Post;
import org.example.accountContent.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3306/userposts";
    private String username = "root";
    private String password = "Jafar_Hussein332";
    private Connection conn = null;
    //connect to database
    public DatabaseConnection() {

    }
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database!");
            createTables();
        } catch (SQLException e) {
            // Properly handle the exception (e.g., log it, throw a custom exception)
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage(), e);
        }
        return conn;
    }




    // Close the connection when you're done with it
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            // Properly handle the exception (e.g., log it)
            System.err.println("Error while closing the connection: " + e.getMessage());
        }
    }


    private void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    username TEXT NOT NULL,\n"
                + "    password TEXT NOT NULL\n"
                + ");";
        String sql2 = "CREATE TABLE IF NOT EXISTS posts (\n"
                + "    id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    title TEXT NOT NULL,\n"
                + "    content TEXT NOT NULL,\n"
                + "    user_id INT NOT NULL DEFAULT 0,\n" // Define the 'user_id' column with a default value here
                + "    FOREIGN KEY (user_id) REFERENCES users (id)\n"
                + ");";
        try {
            conn.createStatement().execute(sql);
            conn.createStatement().execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // createUser method
    public Long createUser(User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return null;
        }
        // The ? is a placeholder for the parameters to be passed to the query and avoid SQL injection
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the parameters
            preparedStatement.setString(1, user.getUsername());

            // Hash the user's password before storing it
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(2, hashedPassword);

            // Execute the query and get the generated keys (including the user ID)
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("User created successfully.");
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1); // Return the generated user ID
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User login(User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return null;
        }
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Set the value for the parameter
            preparedStatement.setString(1, user.getUsername());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password");
                if (BCrypt.checkpw(user.getPassword(), storedPasswordHash)) {
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    System.out.println("Login successful.");
                    // Add other user attributes as needed
                    return user;
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
        System.out.println("Login failed. Please check your username and password.");
        return null;
    }



    //create a post
    // Modify the createPost method to return the ID of the created post
    public Long createPost(Post post) {
        String sql = "INSERT INTO posts (title, content, user_id) VALUES (?, ?, ?)";
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setLong(3, post.getUserId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating post failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long postId = generatedKeys.getLong(1);
                    post.setId(postId); // Set the ID of the post
                    System.out.println("Post created successfully.");
                    return postId;
                } else {
                    throw new SQLException("Creating post failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    //get logged in user's posts
    public List<Post> getUserPost(Post userPost) {
        if (userPost.getUserId() == null) {
            System.out.println("User ID cannot be null.");
            return null;
        }
        String sql = "SELECT * FROM posts WHERE user_id = ?";
        List<Post> posts = new ArrayList<>();
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Set the values for the parameters
            preparedStatement.setLong(1, userPost.getUserId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a new Post object for each result
                userPost.setId(resultSet.getLong("id"));
                userPost.setTitle(resultSet.getString("title"));
                userPost.setContent(resultSet.getString("content"));
                userPost.setUserId(resultSet.getLong("user_id"));
                posts.add(userPost);
                System.out.println("Post retrieved successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }



    // update post
    public boolean updatePost(Post post) {
        if (post.getTitle().isEmpty() || post.getContent().isEmpty()) {
            System.out.println("Title and content cannot be empty.");
            return false;
        }
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Set the values for the parameters
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setLong(3, post.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Post updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    // delete port
    public boolean deletePost(Post post) {
        if (post.getId() == null) {
            System.out.println("Post ID cannot be null.");
            return false;
        }
        String sql = "DELETE from posts WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Set the values for the parameters
            preparedStatement.setLong(1, post.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Post deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    //delete user
    public boolean deleteUser(User user){
        if (user.getId() == null) {
            System.out.println("User ID cannot be null.");
            return false;
        }
        String sql = "DELETE from users where id = ?";
        try(Connection conn = this.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("User deleted successfully.");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

}
