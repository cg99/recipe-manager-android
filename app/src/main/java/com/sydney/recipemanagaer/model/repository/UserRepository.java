package com.sydney.recipemanagaer.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.networking.ApiService;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final ApiService apiService;
    public UserRepository(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        apiService = new ApiService(context);
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

    public User getUser() {
        // Assuming you retrieve a user from a static source, database, or API
        // Here we use a placeholder for demonstration
        return new User("under", "under@example.com", "under","","wrestler with style","https://picsum.photos/id/253/200.jpg", true);

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

    public void login(User user) {
        //
    }
}
