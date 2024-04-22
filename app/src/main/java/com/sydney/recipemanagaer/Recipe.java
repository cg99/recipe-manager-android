package com.sydney.recipemanagaer;

import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private List<String> ingredients; // Changed from String to List<String> to handle multiple ingredients
    private String instructions;
    private int cookingTime;
    private String featuredImgURL;

    // Updated constructor to include the list of ingredients
    public Recipe(String name, String description, List<String> ingredients, String instructions, int cookingTime, String featuredImgURL) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.featuredImgURL = featuredImgURL;
    }

    // Getter for the name
    public String getName() {
        return name;
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
}
