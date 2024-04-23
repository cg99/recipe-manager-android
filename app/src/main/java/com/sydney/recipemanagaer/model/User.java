package com.sydney.recipemanagaer.model;

public class User {
    private String name, email, username, bio, password, profilePic;
    private boolean isAdmin;

    public User(String name, String email, String username, String bio, String profilePic, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.profilePic = profilePic;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
