package com.sydney.recipemanagaer.model.repositories;

import androidx.lifecycle.MutableLiveData;
import com.sydney.recipemanagaer.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {
    public MutableLiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> liveData = new MutableLiveData<>();
        List<Recipe> recipes = new ArrayList<>(); // Simulated data fetching
        // Example recipes as previously detailed
        recipes.add(new Recipe("Chocolate Brownie",
                "A rich and fudgy chocolate brownie",
                Arrays.asList("Chocolate", "Flour", "Sugar", "Eggs"),
                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
                25,
                "https://picsum.photos/id/221/200.jpg"));
        recipes.add(new Recipe("Chocolate Brownie",
                "A rich and fudgy chocolate brownie",
                Arrays.asList("Chocolate", "Flour", "Sugar", "Eggs"),
                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
                25,
                "https://picsum.photos/id/222/200.jpg"));
        // Add other recipes similarly...
        liveData.setValue(recipes);
        return liveData;
    }
}
