package com.sydney.recipemanagaer.model.repository;


//public class RecipeRepository {
//    public MutableLiveData<List<Recipe>> getRecipes() {
//        MutableLiveData<List<Recipe>> liveData = new MutableLiveData<>();
//        List<Recipe> recipes = new ArrayList<>(); // Simulated data fetching
//        // Example recipes as previously detailed
//        recipes.add(new Recipe("Chocolate Brownie",
//                "A rich and fudgy chocolate brownie",
//                Arrays.asList("Chocolate", "Flour", "Sugar", "Eggs"),
//                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
//                25,
//                "https://picsum.photos/id/221/200.jpg"));
//        recipes.add(new Recipe("Chocolate Brownie",
//                "A rich and fudgy chocolate brownie",
//                Arrays.asList("Chocolate", "Flour", "Sugar", "Eggs"),
//                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
//                25,
//                "https://picsum.photos/id/222/200.jpg"));
//        // Add other recipes similarly...
//        liveData.setValue(recipes);
//        return liveData;
//    }
//
//    public MutableLiveData<String> createRecipe(Recipe recipe) {
//        MutableLiveData<String> responseLiveData = new MutableLiveData<>();
//
//        // Simulate network delay using Handler
//        new Handler().postDelayed(() -> {
//            // Simulating a successful post request
//            responseLiveData.setValue("Recipe created successfully!");
//
//            // Simulate an error response
//            // responseLiveData.setValue("Failed to create recipe.");
//        }, 2000);  // Delay of 2 seconds
//
//        return responseLiveData;
//    }
//}


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.networking.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private final ApiService apiService;

    public RecipeRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        apiService = new ApiService(context);
    }

    public LiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> result = new MutableLiveData<>();

        apiService.getRecipes(response -> {
            if (response != null) {
                try {
                    JSONArray recipesArray = response.getJSONArray("recipes");
                    List<Recipe> recipes = parseRecipes(recipesArray);
                    result.setValue(recipes);
                } catch (JSONException e) {
                    Log.e("Get Recipe", "Error parsing JSON", e);
                    result.setValue(null);
                }
            } else {
                result.setValue(null);
            }
        }, error -> {
            result.setValue(null);
            Log.e("Get Recipe", "Error fetching recipes: " + error.getMessage());
        });

        return result;
    }

    public LiveData<String> createRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        MutableLiveData<String> result = new MutableLiveData<>();

        apiService.postRecipe(recipe, response -> {
            if (response != null) {
                result.setValue("Recipe created successfully!");
            } else {
                result.setValue("Error creating recipe: null response");
            }
        }, error -> {
            if (error != null) {
                result.setValue("Error creating recipe: " + error.getMessage());
            } else {
                result.setValue("Error creating recipe: unknown error");
            }
        });

        return result;
    }

    // Deletes a recipe and returns LiveData indicating the result
    public LiveData<String> deleteRecipe(String recipeId) {
        MutableLiveData<String> responseData = new MutableLiveData<>();

        apiService.deleteRecipe(recipeId, response -> {
            // Assuming the server sends back a success message
            responseData.setValue("Deleted successfully");
        }, error -> {
            // Handle the error appropriately
            responseData.setValue("Failed to delete recipe: " + error.toString());
        });

        return responseData;
    }

    private List<Recipe> parseRecipes(JSONArray response) {
        if (response == null) {
            return null;
        }
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject recipeObject = response.getJSONObject(i);
                String recipeId = recipeObject.getString("_id");
                String title = recipeObject.getString("title");
                String description = recipeObject.getString("description");
                List<String> ingredients = new ArrayList<>();
                JSONArray ingredientsArray = recipeObject.getJSONArray("ingredients");
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredients.add(ingredientsArray.getString(j));
                }
                String instructions = recipeObject.getString("instructions");
                int cookingTime = recipeObject.getInt("cookingTime");
                String featuredImgURL = recipeObject.getString("featuredImgURL");
                Recipe recipe = new Recipe(recipeId, title, description, ingredients, instructions, cookingTime, featuredImgURL);
                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipes;
    }
}