package com.sydney.recipemanagaer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel(UserRepository repository) {
        this.userRepository = repository;
    }

    public LiveData<List<User>> getUsers() {
        return userRepository.getUsers();
    }

    public LiveData<String> login(String email, String password) {
       return userRepository.login(email, password);
    }

    public LiveData<String> signup(User user) {
        return userRepository.signup(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
        // Reload the user list or handle the update UI accordingly
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
        // Reload the user list or handle the update UI accordingly
    }

    public String getToken() {
        return userRepository.getToken();
    }


}
