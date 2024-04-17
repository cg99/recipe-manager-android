package com.sydney.recipemanagaer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView textViewLogin = findViewById(R.id.textViewLogin);

        fullNameEditText = findViewById(R.id.signup_fullname);
        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        confirmPasswordEditText = findViewById(R.id.signup_confirm_password);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> {

            // Retrieve input from EditText fields
            String fullName = fullNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // if conditions to check the empty fields
            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
            //if condition to check email
            if (!isValidEmail(email)) {
                Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // if condition to check the passwords
            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            //code to signup and redirect to homepage
            Toast.makeText(this, "Signup button pressed", Toast.LENGTH_SHORT).show();

        });

        textViewLogin.setOnClickListener(view -> {
            // Navigate to the RegisterActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Method to check format of email
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}