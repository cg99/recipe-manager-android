package com.sydney.recipemanagaer.networking.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @Multipart
    @POST("recipe")
    Call<Void> postRecipe(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("ingredients") RequestBody ingredients,
            @Part("instructions") RequestBody instructions,
            @Part("cookingTime") RequestBody cookingTime,
            @Part("createdBy") RequestBody createdBy,
            @Part MultipartBody.Part featuredImage,
            @Part List<MultipartBody.Part> images,
            @Part("foodType") RequestBody foodType

    );

    @Multipart
    @PATCH("recipe/{id}")
    Call<Void> updateRecipe(
            @Path("id") String recipeId,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("ingredients") RequestBody ingredients,
            @Part("instructions") RequestBody instructions,
            @Part("cookingTime") RequestBody cookingTime,
            @Part MultipartBody.Part featuredImage,
            @Part List<MultipartBody.Part> images,
            @Part("foodType") RequestBody foodType
    );

    @GET("recipe")
    Call<ResponseBody> getRecipes();

    @POST("user/signup")
    Call<ResponseBody> register(
            @Part("fullname") RequestBody fullname,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("confirmPassword") RequestBody confirmPassword

    );

    @POST("user/login")
    Call<ResponseBody> login(
            @Body RequestBody requestBody
    );

    @GET("recipe/favorites/{userId}")
    Call<ResponseBody> getUserFavorites(@Path("userId") String userId);

    @GET("recipe/myrecipe/{userId}")
    Call<ResponseBody> getUserRecipes(@Path("userId") String userId);

    @DELETE("recipe/{recipeId}")
    Call<String> deleteRecipe(@Path("recipeId") String recipeId);

    @GET("user/{userId}")
    Call<ResponseBody> getUserById(@Path("userId") String userId, @Header("Authorization") String token);

    @PATCH("recipe/{recipeId}/favorite")
    Call<ResponseBody> markRecipeAsFavorite(@Path("recipeId") String recipeId, @Query("userId") String userId);
}
