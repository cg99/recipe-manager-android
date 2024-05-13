package com.sydney.recipemanagaer.model.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.networking.ApiService;
import com.sydney.recipemanagaer.networking.retrofit.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private final ApiService apiService;
    private final RetrofitService retrofitService;
    UserRepository userRepository;

    public RecipeRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        apiService = new ApiService(context);
        userRepository = new UserRepository(context);
        retrofitService = new RetrofitService(context);
    }

    public LiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> result = new MutableLiveData<>();

        retrofitService.getRecipes(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray recipesArray = jsonObject.getJSONArray("recipes");
                        Log.d("retro", recipesArray + " " + response);
                        List<Recipe> recipes = parseRecipes(recipesArray);
                        result.setValue(recipes);
                    } catch (JSONException | IOException e) {
                        Log.e("retro", "Error parsing response", e);
                        result.setValue(null);
                    }
                } else {
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                result.setValue(null);
                Log.e("Get Recipe", "Error fetching recipes: " + t.getMessage());
            }
        });

        return result;
    }

    public LiveData<String> createRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        MutableLiveData<String> result = new MutableLiveData<>();
        String userId = userRepository.getLoggedInUserId();


        retrofitService.postRecipe(recipe, userId, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    result.postValue("Recipe created successfully!");
                } else {
                    // Handle response error
                    result.postValue("Error creating recipe: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                result.postValue("Error creating recipe: " + t.getMessage());
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

        retrofitService.updateRecipe(recipe, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    result.postValue("Recipe updated successfully!");
                } else {
                    // Handle response error
                    result.postValue("Error updating recipe: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                result.postValue("Error updating recipe: " + t.getMessage());
            }
        });

        return result;
    }

    public LiveData<String> markAsFavorite(String recipeId)
    {
        MutableLiveData<String> result = new MutableLiveData<>();
        String userId = userRepository.getLoggedInUserId();

        apiService.markRecipeAsFavorite(recipeId, userId, response -> {
            result.setValue(response.optString("message", "Marked as favorite"));
        }, error -> {
            result.setValue("Failed to mark as favorite");
        });
        return result;
    }

    public LiveData<List<Recipe>> getUserFavorites() {
        MutableLiveData<List<Recipe>> favoritesLiveData = new MutableLiveData<>();
        String userId = userRepository.getLoggedInUserId();

        retrofitService.getUserFavorites(userId, new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray recipesArray = jsonObject.getJSONArray("recipes");
//                        Log.d("retro", recipesArray + " " + response);
                        List<Recipe> recipes = parseRecipes(recipesArray);
                        favoritesLiveData.setValue(recipes);
                    } catch (JSONException | IOException e) {
                        Log.e("retro", "Error parsing response", e);
                        favoritesLiveData.setValue(null);
                    }
                } else {
                    favoritesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                favoritesLiveData.setValue(null);
                Log.e("Get Recipe", "Error fetching recipes: " + t.getMessage());
            }
        });
        return favoritesLiveData;
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

//                Log.i("Ingredient", ingredientsArray.toString());
                String instructions = recipeObject.optString("instructions", "no instructions");
                int cookingTime = recipeObject.getInt("cookingTime");
                String featuredImgURL = recipeObject.getString("featuredImgURL");

                ArrayList<String> imagesURL = new ArrayList<>();
                JSONArray imagesURLArray = recipeObject.getJSONArray("imagesURL");
                for (int j = 0; j < imagesURLArray.length(); j++) {
                    imagesURL.add(imagesURLArray.getString(j));
                }
                Recipe recipe = new Recipe(recipeId, title, description, ingredients, instructions, cookingTime);

                recipe.setFeaturedImgURL(featuredImgURL);
                recipe.setImages(imagesURL);

                String foodType = recipeObject.optString("foodType", "no type");
                recipe.setFoodType(foodType);

                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipes;
    }


    public LiveData<List<Recipe>> getUserRecipes() {
        MutableLiveData<List<Recipe>> userRecipesLiveData = new MutableLiveData<>();
        String userId = userRepository.getLoggedInUserId();

        retrofitService.getUserRecipes(userId, new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray recipesArray = jsonObject.getJSONArray("recipes");
//                        Log.d("retro", recipesArray + " " + response);
                        List<Recipe> recipes = parseRecipes(recipesArray);
                        userRecipesLiveData.setValue(recipes);
                    } catch (JSONException | IOException e) {
                        Log.e("retro", "Error parsing response", e);
                        userRecipesLiveData.setValue(null);
                    }
                } else {
                    userRecipesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userRecipesLiveData.setValue(null);
                Log.e("Get Recipe", "Error fetching recipes: " + t.getMessage());
            }
        });
        return userRecipesLiveData;
    }
}