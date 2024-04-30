package com.sydney.recipemanagaer.networking;

import org.json.JSONObject;

public interface LoginResponseListener {
    void onFailure(String error);

    void onSuccess(String token, String userId, JSONObject userData);
}
