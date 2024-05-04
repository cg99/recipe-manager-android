package com.sydney.recipemanagaer.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String title;
    private String description;
    private List<String> ingredients;
    private String instructions;
    private int cookingTime;
    private String featuredImgURL;
    private String recipeId;

    private List<String> favoritedBy;
    private String featuredImage;

    private ArrayList<String> images;


    public Recipe(String title, String description, List<String> ingredients, String instructions, int cookingTime) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
    }

    public Recipe(String recipeId, String title, String description, List<String> ingredients, String instructions, int cookingTime) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
    }

    // Getter for the name
    public String getTitle() {
        return title;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Getter for ingredients
    public List<String> getIngredients() {
        return ingredients;
    }

    // Getter for instructions
    public String getInstructions() {
        return instructions;
    }

    // Getter for cooking time
    public int getCookingTime() {
        return cookingTime;
    }

    // Getter for the featured image URL
    public String getFeaturedImgURL() {
        return featuredImgURL;
    }

    public void setFeaturedImgURL(String featuredImgURL) {
        this.featuredImgURL = featuredImgURL;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public List<String> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(List<String> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public void setFeaturedImage(String imageUri) {
        this.featuredImage = imageUri;
    }

    public String getFeaturedImage() {
        return this.featuredImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
