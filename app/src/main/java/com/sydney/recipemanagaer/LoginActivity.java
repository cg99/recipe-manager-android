package com.sydney.recipemanagaer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (userIsLoggedIn()) {
            navigateToMainActivity();
        }


        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewRegister = findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(view -> {
            // Validate input
            String username = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Perform validation
            if (isValidCredentials(username, password)) {
                // If provided credentials are valid then go to homepage
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                // If credentials are invalid, show an error message
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
            navigateToMainActivity();
        });

        textViewRegister.setOnClickListener(view -> {
            // Navigate to the RegisterActivity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

    }
    private boolean isValidCredentials(String username, String password) {
        //check if both fields are non-empty
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

    private boolean userIsLoggedIn() {
        // Check for the existence of a session token or similar
        // This could involve checking SharedPreferences, a database, or some other form of storage
        return false; // Return true if a valid session exists, false otherwise
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        // Optionally, add this to prevent the user from going back to the login screen with the back button after logging in
        finish();
    }

}