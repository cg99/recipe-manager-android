package com.sydney.recipemanagaer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.model.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();

    public LiveData<List<User>> getUsers() {
        return userRepository.getUsers();
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
        // Reload the user list or handle the update UI accordingly
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
        // Reload the user list or handle the update UI accordingly
    }


}
