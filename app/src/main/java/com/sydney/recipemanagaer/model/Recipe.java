package com.sydney.recipemanagaer.model;

import java.util.List;

public class Recipe {
    private String title;
    private String description;
    private List<String> ingredients;
    private String instructions;
    private int cookingTime;
    private String featuredImgURL;
    private String recipeId;

    // Updated constructor to include the list of ingredients
    public Recipe(String title, String description, List<String> ingredients, String instructions, int cookingTime, String featuredImgURL) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.featuredImgURL = featuredImgURL;
    }

    public Recipe(String recipeId, String title, String description, List<String> ingredients, String instructions, int cookingTime, String featuredImgURL) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.featuredImgURL = featuredImgURL;
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

    public String getRecipeId(){
        return recipeId;
    }
}
