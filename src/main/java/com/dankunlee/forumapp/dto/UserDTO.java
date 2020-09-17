package com.dankunlee.forumapp.dto;

public class UserDTO {
    // Separate DTO class for User
    // Only used when containing http session information of User
    // Sends data to service or controllers (more secure than directly using User entitiy)
    // Used in LogInController
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
