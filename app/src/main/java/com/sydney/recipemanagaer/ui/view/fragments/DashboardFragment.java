package com.sydney.recipemanagaer.ui.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.repository.UserRepository;
import com.sydney.recipemanagaer.ui.view.activities.LoginActivity;
import com.sydney.recipemanagaer.ui.viewmodel.DashboardViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.DashboardViewModelFactory;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ImageView userProfile = view.findViewById(R.id.imageViewUserProfile);
        TextView userName = view.findViewById(R.id.textViewUserName);
        TextView userEmail = view.findViewById(R.id.textViewUserEmail);
        TextView userBio = view.findViewById(R.id.textViewUserBio);
        Button logoutButton = view.findViewById(R.id.buttonLogout);

        UserRepository userRepository = new UserRepository(getContext());
        viewModel = new ViewModelProvider(this, new DashboardViewModelFactory(userRepository)).get(DashboardViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                userName.setText(user.getUsername());
                userEmail.setText(user.getEmail());
                userBio.setText(user.getBio());

                // Load the profile image with Glide
                Glide.with(this).load(user.getProfilePic()).into(userProfile);
            }
        });

        // my recipes and admin dashboard links
        Button btnAdminDashboard = view.findViewById(R.id.btnAdminDashboard);

        viewModel.getIsAdminLiveData().observe(getViewLifecycleOwner(), isAdmin -> {
            if (isAdmin) {
                btnAdminDashboard.setVisibility(View.VISIBLE);
            } else {
                btnAdminDashboard.setVisibility(View.GONE);
            }
        });

        btnAdminDashboard.setOnClickListener(v -> {
            // Navigate to Admin Dashboard Fragment to show user list
            Fragment adminFragment = new AdminFragment(); // Create a new instance of your admin fragment
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Obtain the FragmentManager
            FragmentTransaction transaction = fragmentManager.beginTransaction(); // Begin the transaction
            transaction.replace(R.id.fragment_container, adminFragment); // Replace the current fragment
            transaction.addToBackStack(null); // Add this transaction to the back stack (optional)
            transaction.commit(); // Commit the transaction
        });

        logoutButton.setOnClickListener(v -> {
            viewModel.clearSession();

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
            startActivity(intent);
            getActivity().finish();  // Ensure this activity is cleared from the stack
        });

        return view;
    }
}
