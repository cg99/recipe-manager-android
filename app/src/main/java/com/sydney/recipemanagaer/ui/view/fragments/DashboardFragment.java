package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.utils.Util;


public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ImageView userProfile = view.findViewById(R.id.imageViewUserProfile);
        TextView userName = view.findViewById(R.id.textViewUserName);
        TextView userEmail = view.findViewById(R.id.textViewUserEmail);
        TextView userBio = view.findViewById(R.id.textViewUserBio);

        // Assuming you have a method getUser() that returns a User object
        User user = Util.getUser();
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        userBio.setText(user.getBio());

        // Load the profile image with Glide or Picasso
        Glide.with(this).load(user.getProfilePic()).into(userProfile);

        return view;
    }
}