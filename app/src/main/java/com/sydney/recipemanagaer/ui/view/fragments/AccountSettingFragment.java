package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.User;
import com.sydney.recipemanagaer.model.repository.UserRepository;
import com.sydney.recipemanagaer.ui.viewmodel.UserViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.UserViewModelFactory;
import com.sydney.recipemanagaer.utils.Util;

public class AccountSettingFragment extends Fragment {
    private EditText editTextUserName, editTextUserBio, editTextUserEmail, editTextUserPassword;
    private ImageView imageViewSelected;
    private Button buttonUpdateUser;
    private UserViewModel viewModel;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        UserRepository userRepository = new UserRepository(getContext());
        viewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        initViews(view);
        populateFields();
        setListeners(view);

        return view;
    }

    private void initViews(View view) {
        editTextUserName = view.findViewById(R.id.editTextUserName);
        editTextUserBio = view.findViewById(R.id.editTextUserBio);
        editTextUserEmail = view.findViewById(R.id.editTextUserEmail);
        editTextUserPassword = view.findViewById(R.id.editTextPassword);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        buttonUpdateUser = view.findViewById(R.id.btnUpdateUser);
    }

    private void populateFields() {
        Bundle args = getArguments();
        if (args != null) {
            userId = args.getString("userId");
            editTextUserName.setText(args.getString("name"));
            editTextUserBio.setText(args.getString("bio"));
            editTextUserEmail.setText(args.getString("email"));
            editTextUserPassword.setText(args.getString("password"));

            Glide.with(this)
                    .load(Util.getBaseURL() + "users/images/" + args.getString("userImage"))
                    .placeholder(R.drawable.placeholder_image_background)
                    .error(R.drawable.error_image)
                    .into(imageViewSelected);
        } else {
            Toast.makeText(getContext(), "No user data found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListeners(View view) {
        buttonUpdateUser.setOnClickListener(v -> updateUser());
    }

    private void updateUser() {
        String name = editTextUserName.getText().toString();
        String bio = editTextUserBio.getText().toString();
        String email = editTextUserEmail.getText().toString();
        String password = editTextUserPassword.getText().toString();

        User updatedUser = new User(userId, name, email, "", bio, "", "admin");

//        viewModel.updateUser(updatedUser).observe(getViewLifecycleOwner(), result -> {
//            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
//            Util.navigateToMainActivity(getContext());
//        });
    }
}
