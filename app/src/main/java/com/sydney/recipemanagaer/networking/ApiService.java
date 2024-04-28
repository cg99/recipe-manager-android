package com.sydney.recipemanagaer.networking;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sydney.recipemanagaer.model.Recipe;

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
            jsonObject.put("name", recipeData.getName());
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

    // Method to delete a recipe by ID
    public void deleteRecipe(String recipeId, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/recipe/" + recipeId; // Adjust URL to include the recipe ID
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                responseListener,
                errorListener);
        networkingClient.getRequestQueue().add(stringRequest);
    }
}