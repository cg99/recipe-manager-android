package com.sydney.recipemanagaer.ui.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.repository.UserRepository;
import com.sydney.recipemanagaer.ui.view.fragments.CreateRecipeFragment;
import com.sydney.recipemanagaer.ui.view.fragments.DashboardFragment;
import com.sydney.recipemanagaer.ui.view.fragments.FavoriteFragment;
import com.sydney.recipemanagaer.ui.view.fragments.HomeFragment;
import com.sydney.recipemanagaer.ui.viewmodel.UserViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.UserViewModelFactory;


public class MainActivity extends AppCompatActivity {
    UserRepository userRepository;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!userIsLoggedIn()) {
            navigateToLoginActivity();
            return; // Stop further execution of this method
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Create fragments one time
        Fragment homeFragment = new HomeFragment();
        Fragment dashboardFragment = new DashboardFragment();
        Fragment createRecipeFragment = new CreateRecipeFragment();
        Fragment favoriteFragment = new FavoriteFragment();

        // Initialize with home fragment
        fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.homeFragment) {
                selectedFragment = homeFragment;
            } else if (id == R.id.dashboardFragment) {
                selectedFragment = dashboardFragment;
            } else if (id == R.id.createRecipeFragment) {
                selectedFragment = createRecipeFragment;
            } else if (id == R.id.favoriteFragment) {
                selectedFragment = favoriteFragment;
            }

            if (selectedFragment != null) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true; // Return true to display the item as the selected item
        });

    // Set the home fragment as default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

    }

    private boolean userIsLoggedIn() {
        userRepository = new UserRepository(this);
        viewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        String token = viewModel.getToken();
        return token != null && !token.isEmpty();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();  // Finish MainActivity to remove it from the back stack
    }


}