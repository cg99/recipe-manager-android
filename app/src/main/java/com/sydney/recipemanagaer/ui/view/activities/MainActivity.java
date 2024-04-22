package com.sydney.recipemanagaer.ui.view.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sydney.recipemanagaer.ui.view.fragments.CreateRecipeFragment;
import com.sydney.recipemanagaer.ui.view.fragments.DashboardFragment;
import com.sydney.recipemanagaer.ui.view.fragments.FavoriteFragment;
import com.sydney.recipemanagaer.ui.view.fragments.HomeFragment;
import com.sydney.recipemanagaer.R;


public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.homeFragment) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.dashboardFragment) {
                selectedFragment = new DashboardFragment();
            } else if (id == R.id.createRecipeFragment) {
                selectedFragment = new CreateRecipeFragment();
            } else if (id == R.id.favoriteFragment) {
                selectedFragment = new FavoriteFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true; // Return true to display the item as the selected item
        });

        // Set the home fragment as default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }


        // nothing
//        textView = findViewById(R.id.text_view);
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//
//        // server
//        String url = "http://10.0.2.2:3000/recipe"; // Your URL
////        String url ="https://recipe-manager-server.onrender.com"; // server URL
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        textView.setText("Response is: " + response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error);
//                textView.setText("That didn't work!");
//            }
//        });
//
//        queue.add(stringRequest);
    }


}