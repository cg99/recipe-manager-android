package com.sydney.recipemanagaer.model;

public class Review {
    private String username;
    private float rating;
    private String comment;

    // Constructor
    public Review(String username, float rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    // Getter and Setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter methods for rating
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // Getter and Setter methods for comment
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

