package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.ui.view.adapters.UserAdapter;
import com.sydney.recipemanagaer.ui.viewmodel.UserViewModel;

public class AdminFragment extends Fragment implements UserAdapter.OnUserListener {
    private UserViewModel viewModel;
    private RecyclerView usersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        usersRecyclerView = view.findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            UserAdapter adapter = new UserAdapter(users, null);
            usersRecyclerView.setAdapter(adapter);
        });

        return view;
    }

    @Override
    public void onEditUser(User user) {
        // Show an edit dialog or navigate to an edit screen
        viewModel.updateUser(user);
    }

    @Override
    public void onDeleteUser(String userId) {
        viewModel.deleteUser(userId);
    }
}
