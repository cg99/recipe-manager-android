package com.sydney.recipemanagaer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.model.repositories.UserRepository;

public class DashboardViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private UserRepository userRepository = new UserRepository();
    private MutableLiveData<Boolean> isAdminLiveData = new MutableLiveData<>();


    public DashboardViewModel() {
        loadUser();
    }

    private void loadUser() {
        User user = userRepository.getUser();  // Get the user from the repository
        userLiveData.setValue(user);
        isAdminLiveData.setValue(user.isAdmin()); // Assume User model has an isAdmin method
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getIsAdminLiveData() {
        return isAdminLiveData;
    }
}
