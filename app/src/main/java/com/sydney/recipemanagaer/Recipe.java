package com.sydney.recipemanagaer;

public class Recipe {
    private String name;
    private String description;

    private String featuredImgURL;

    public Recipe(String name, String description, String featuredImgURL) {
        this.name = name;
        this.description = description;
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
}
