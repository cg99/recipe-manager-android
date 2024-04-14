package com.sydney.recipemanagaer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView textViewLogin = findViewById(R.id.textViewLogin);

        Button buttonSignup = findViewById(R.id.signup_button);
        buttonSignup.setOnClickListener(view -> {
            Toast.makeText(this, "Signup button pressed", Toast.LENGTH_SHORT).show();
          //code to signup and redirect to homepage
        });

        textViewLogin.setOnClickListener(view -> {
            // Navigate to the RegisterActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}