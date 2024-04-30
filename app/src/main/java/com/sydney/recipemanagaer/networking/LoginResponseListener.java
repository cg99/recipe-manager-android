package com.sydney.recipemanagaer.networking;

public interface LoginResponseListener {
    void onSuccess(String token);
    void onFailure(String error);
}
