package com.sydney.recipemanagaer;

import static com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


        textView = findViewById(R.id.text_view);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:3000/recipe"; // Your URL
//        String url ="https://recipe-manager-server.onrender.com"; // server URL

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    private OnNavigationItemSelectedListener navListener =
            new OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.nav_dashboard) {
                        selectedFragment = new DashboardFragment();
                    } else if (itemId == R.id.nav_create) {
                        selectedFragment = new CreateRecipeFragment();
                    } else if (itemId == R.id.nav_favorite) {
                        selectedFragment = new FavoriteFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    @Override
    protected  void onStart() {
        super.onStart();
        System.out.println("Start");
    }

    @Override
    protected  void onResume() {
        super.onResume();
        System.out.println("Resume");
    }

    @Override
    protected  void onPause() {
        super.onPause();
        System.out.println("Pause");
    }

    @Override
    protected  void onStop() {
        super.onStop();
        System.out.println("Stop");
    }

    @Override
    protected  void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy");
    }

    @Override
    protected  void onRestart() {
        super.onRestart();
        System.out.println("Restart");
    }

}