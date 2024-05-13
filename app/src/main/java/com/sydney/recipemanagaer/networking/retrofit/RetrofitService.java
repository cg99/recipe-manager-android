package com.sydney.recipemanagaer.networking.retrofit;

import android.content.Context;
import android.util.Log;

import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService {
    private final ApiService apiService;

    public RetrofitService(Context context) {
        this.apiService = RetrofitClient.getApiService(context);
    }

    public void getRecipes(Callback<ResponseBody> retrofitCallback) {
        // Make API call
        Call<ResponseBody> call = apiService.getRecipes();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "Recipes fetched successfully.");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitService", "Failed to fetch recipes: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }

    public void postRecipe(Recipe recipeData, String userId, retrofit2.Callback<Void> retrofitCallback) {
        // Prepare text parameters
        RequestBody titleBody = RequestBody.create(recipeData.getTitle(), MediaType.parse("text/plain"));
        RequestBody descriptionBody = RequestBody.create(recipeData.getDescription(), MediaType.parse("text/plain"));
        JSONArray jsonArray = new JSONArray(recipeData.getIngredients());
        RequestBody ingredientsBody = RequestBody.create(jsonArray.toString(), MediaType.parse("application/json"));
        RequestBody instructionsBody = RequestBody.create(recipeData.getInstructions(), MediaType.parse("text/plain"));
        RequestBody cookingTimeBody = RequestBody.create(Integer.toString(recipeData.getCookingTime()), MediaType.parse("text/plain"));
        RequestBody createdByBody = RequestBody.create(userId, MediaType.parse("text/plain"));
        RequestBody foodTypeBody = RequestBody.create(recipeData.getFoodType(), MediaType.parse("text/plain"));


        // Prepare file parameters
        MultipartBody.Part featuredImagePart = null;
        File featuredImageFile = new File(recipeData.getFeaturedImage());
        if (featuredImageFile.exists()) {
            RequestBody fileBody = RequestBody.create(featuredImageFile, MediaType.parse("image/*"));
            featuredImagePart = MultipartBody.Part.createFormData("featuredImage", featuredImageFile.getName(), fileBody);
        }

        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (int i = 0; i < recipeData.getImages().size(); i++) {
            File file = new File(recipeData.getImages().get(i));
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
                MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
                imageParts.add(part);
            }
        }

        // Make API call
        Call<Void> call = apiService.postRecipe(
                titleBody, descriptionBody, ingredientsBody, instructionsBody,
                cookingTimeBody, createdByBody, featuredImagePart, imageParts, foodTypeBody
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "Recipe posted successfully.");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RetrofitService", "Failed to post recipe: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }

    public void updateRecipe(Recipe recipeData, retrofit2.Callback<Void> retrofitCallback) {
        // Prepare text parameters
        RequestBody titleBody = RequestBody.create(recipeData.getTitle(), MediaType.parse("text/plain"));
        RequestBody descriptionBody = RequestBody.create(recipeData.getDescription(), MediaType.parse("text/plain"));
        RequestBody ingredientsBody = RequestBody.create(new JSONArray(recipeData.getIngredients()).toString(), MediaType.parse("application/json"));
        RequestBody instructionsBody = RequestBody.create(recipeData.getInstructions(), MediaType.parse("text/plain"));
        RequestBody cookingTimeBody = RequestBody.create(Integer.toString(recipeData.getCookingTime()), MediaType.parse("text/plain"));
        RequestBody foodTypeBody = RequestBody.create(recipeData.getFoodType(), MediaType.parse("text/plain"));

        // Prepare file parameters (Handle null or empty paths)
        MultipartBody.Part featuredImagePart = null;
        String featuredImagePath = recipeData.getFeaturedImage();
        if (featuredImagePath != null && !featuredImagePath.isEmpty()) {
            File featuredImageFile = new File(featuredImagePath);
            if (featuredImageFile.exists()) {
                RequestBody fileBody = RequestBody.create(featuredImageFile, MediaType.parse("image/*"));
                featuredImagePart = MultipartBody.Part.createFormData("featuredImage", featuredImageFile.getName(), fileBody);
            }
        }

        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (String imagePath : recipeData.getImages()) {
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
                    MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
                    imageParts.add(part);
                }
            }
        }

        // Make API call
        Call<Void> call = apiService.updateRecipe(
                recipeData.getRecipeId(), titleBody, descriptionBody, ingredientsBody,
                instructionsBody, cookingTimeBody, featuredImagePart, imageParts, foodTypeBody
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    retrofitCallback.onResponse(call, response);
                } else {
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                retrofitCallback.onFailure(call, t);
            }
        });
    }

    public void register(User user, retrofit2.Callback<ResponseBody> retrofitCallback) {
        // Prepare text parameters
        RequestBody fullnameBody = RequestBody.create(user.getFullName(), MediaType.parse("text/plain"));
        RequestBody emailBody = RequestBody.create(user.getEmail(), MediaType.parse("text/plain"));
        RequestBody passwordBody = RequestBody.create(user.getPassword(), MediaType.parse("text/plain"));
        RequestBody confirmPassword = RequestBody.create(user.getConfirmPassword(), MediaType.parse("text/plain"));

        // Make API call
        Call<ResponseBody> call = apiService.register(fullnameBody, emailBody, passwordBody, confirmPassword);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "User registered successfully.");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitService", "Failed to register user: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }

    public void login(String email, String password,  retrofit2.Callback<ResponseBody> retrofitCallback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        // Convert the JSONObject to a RequestBody
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));


        Call<ResponseBody> call = apiService.login(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "Login Successful");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitService", "Failed to login: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }


    public void getUserFavorites(String userId, retrofit2.Callback<ResponseBody> retrofitCallback) {
        Call<ResponseBody> call = apiService.getUserFavorites(userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "Get User Favorites Successful");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitService", "Failed to get user favorites: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }

    public void getUserRecipes(String userId, retrofit2.Callback<ResponseBody> retrofitCallback) {
        Call<ResponseBody> call = apiService.getUserRecipes(userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("RetrofitService", "Get User Recipes Successful");
                    retrofitCallback.onResponse(call, response);
                } else {
                    Log.e("RetrofitService", "Error in response: " + response.message());
                    retrofitCallback.onFailure(call, new Throwable("Response Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitService", "Failed to get user recipes: " + t.getMessage());
                retrofitCallback.onFailure(call, t);
            }
        });
    }


}
