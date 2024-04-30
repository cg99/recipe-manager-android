package com.sydney.recipemanagaer.networking;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiService {
    private final NetworkingClient networkingClient;
    private static final String BASE_URL = "http://10.0.2.2:8000/api/v1";

    public ApiService(Context context) {
        networkingClient = NetworkingClient.getInstance(context);
    }

    // Method to fetch recipes using GET
    public void getRecipes(Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/recipe";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }

    // Method to post a new recipe using POST
    public void postRecipe(Recipe recipeData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/recipe";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", recipeData.getTitle());
            jsonObject.put("description", recipeData.getDescription());
            jsonObject.put("ingredients", new JSONArray(recipeData.getIngredients()));
            jsonObject.put("instructions", recipeData.getInstructions());
            jsonObject.put("cookingTime", recipeData.getCookingTime());
            jsonObject.put("featuredImgURL", recipeData.getFeaturedImgURL());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener);
        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }

    public void updateRecipe(Recipe recipeData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/recipe/" + recipeData.getRecipeId();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", recipeData.getTitle());
            jsonObject.put("description", recipeData.getDescription());
            jsonObject.put("ingredients", new JSONArray(recipeData.getIngredients()));
            jsonObject.put("instructions", recipeData.getInstructions());
            jsonObject.put("cookingTime", recipeData.getCookingTime());
            jsonObject.put("featuredImgURL", recipeData.getFeaturedImgURL());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonObject, responseListener, errorListener);
        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }

    // Method to delete a recipe by ID
    public void deleteRecipe(String recipeId, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/recipe/" + recipeId; // Adjust URL to include the recipe ID
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                responseListener,
                errorListener);
        networkingClient.getRequestQueue().add(stringRequest);
    }

    public void login(String email, String password, final LoginResponseListener listener) {
        String url = BASE_URL + "/user/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            listener.onFailure("Error creating login request: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        // Assume the server sends the token in a field called "token"
                        String token = response.getString("token");
                        listener.onSuccess(token);
                    } catch (JSONException e) {
                        listener.onFailure("Failed to parse token from response: " + e.getMessage());
                    }
                },
                error -> {
                    // Handle different types of errors here (e.g., network, timeout, etc.)
                    String errorMsg = "Network error";
                    if (error instanceof AuthFailureError) {
                        errorMsg = "Authentication failed";
                    } else if (error.getMessage() != null) {
                        errorMsg = error.getMessage();
                    }
                    listener.onFailure(errorMsg);
                });

        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }


    public void register(User user, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/user/signup";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullname", user.getFullName());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener);

        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }

    // Method to get a user's data by ID
    public void getUserData(String userId, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/user/" + userId; // Adjust URL to include the user ID
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        networkingClient.getRequestQueue().add(jsonObjectRequest);
    }

}