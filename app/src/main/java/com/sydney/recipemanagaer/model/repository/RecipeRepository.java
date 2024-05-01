package com.sydney.recipemanagaer.model.repository;


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
    UserRepository userRepository;

    public RecipeRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        apiService = new ApiService(context);
        userRepository = new UserRepository(context);
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
        String userId = userRepository.getLoggedInUserId();

        apiService.postRecipe(recipe, userId, response -> {
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

    public LiveData<String> updateRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        MutableLiveData<String> result = new MutableLiveData<>();

        apiService.updateRecipe(recipe, response -> {
            if (response != null) {
                result.setValue("Recipe updated successfully!");
            } else {
                result.setValue("Error updating recipe: null response");
            }
        }, error -> {
            if (error != null) {
                result.setValue("Error updating recipe: " + error.getMessage());
            } else {
                result.setValue("Error updating recipe: unknown error");
            }
        });

        return result;
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
                String instructions = recipeObject.optString("instructions", "no instructions");
                int cookingTime = recipeObject.getInt("cookingTime");
                String featuredImgURL = recipeObject.optString("featuredImgURL", "https://images.unsplash.com/photo-1546069901-ba9599a7e63c");
                Recipe recipe = new Recipe(recipeId, title, description, ingredients, instructions, cookingTime, featuredImgURL);
                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipes;
    }
}