package com.sydney.recipemanagaer;

public class Recipe {
    private String name;
    private String description;
    private String instructions;
    private int cookingTime;
    private String featuredImgURL;

    public Recipe(String name, String description, String instructions, int cookingTime, String featuredImgURL) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.featuredImgURL = featuredImgURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFeaturedImgURL() {

        return featuredImgURL;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public String getInstructions() {
        return instructions;
    }
}
