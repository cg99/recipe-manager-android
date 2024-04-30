package com.sydney.recipemanagaer.model.repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.networking.ApiService;
import com.sydney.recipemanagaer.networking.LoginResponseListener;
import com.sydney.recipemanagaer.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final ApiService apiService;
    private MutableLiveData<String> loginStatus = new MutableLiveData<>();
    private Context context;

    private SharedPreferences sharedPreferences;


    public UserRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        apiService = new ApiService(context);
        sharedPreferences = context.getSharedPreferences(Util.SHARED_PREFS_FILE, MODE_PRIVATE);
    }

    public MutableLiveData<List<User>> getUsers() {
        // This should be replaced with actual data fetching logic
        MutableLiveData<List<User>> liveData = new MutableLiveData<>();
        List<User> users = new ArrayList<>();

        users.add(new User( "LohnJoe", "lohn@example.com", "lony","", "fitness influencer",  "https://picsum.photos/id/251/200.jpg",false));
        users.add(new User("JaneDoe", "jane@example.com", "jany","","youtuber with style","https://picsum.photos/id/252/200.jpg", false));
        // Add more users
        liveData.setValue(users);
        return liveData;
    }

    public MutableLiveData<User> getUser(String userId) {
        // Assuming you retrieve a user from a static source, database, or API
        // Here we use a placeholder for demonstration
        MutableLiveData<User> liveData = new MutableLiveData<>();

        User user = new User("under",
                "under@example.com",
                "under","",
                "wrestler with style",
                "https://picsum.photos/id/253/200.jpg",
                true);
        liveData.setValue(user);
        return liveData;
    }

    public void updateUser(User user) {
        // update logic
    }

    public void deleteUser(String id) {
        // delete logic
    }

    public LiveData<String> signup(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        MutableLiveData<String> result = new MutableLiveData<>();
        apiService.register(user, response -> {
            if (response != null) {
                result.setValue("Signup Successful");
            } else {
                result.setValue(null);
            }
        }, error -> {
            if (error != null) {
                result.setValue("Error creating user: " + error);
            } else {
                result.setValue(null);
            }
        });

        return result;
    }

    public LiveData<String> getLoginStatus() {
        return loginStatus;
    }

    public LiveData<String> login(String email, String password) {
        apiService.login(email, password, new LoginResponseListener() {
            @Override
            public void onSuccess(String token, String userId, JSONObject userData) {
                // Store the token securely, then update LiveData
                saveToken(token, userId); // Implement this method to save token securely
                loginStatus.postValue("Login successful");
            }

            @Override
            public void onFailure(String error) {
                loginStatus.postValue("Login failed: " + error);
            }
        });

        return loginStatus;
    }

    private void saveToken(String token, String userId) {
        // Use SharedPreferences or secure storage to save the token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Util.TOKEN_KEY, token);
        editor.putString(Util.USER_ID_KEY, userId);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(Util.TOKEN_KEY, null);
    }

    public String getLoggedInUserId() {
        return sharedPreferences.getString(Util.USER_ID_KEY, null);
    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Util.TOKEN_KEY);
        editor.remove(Util.USER_ID_KEY);
        editor.apply();
    }
}
