package com.sydney.recipemanagaer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = view.findViewById(R.id.search_view);
        Button filterButton = view.findViewById(R.id.button_filter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform the search when query is submitted
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform live filtering here
                return true;
            }
        });


        filterButton.setOnClickListener(v -> {
            // Open filter dialog or activity
        });


        // Example data
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Chocolate Cake", "Rich and creamy", "https://picsum.photos/200.jpg"));
        recipes.add(new Recipe("Pasta Carbonara", "Delicious Italian pasta", "https://picsum.photos/200/300.jpg"));
        recipes.add(new Recipe("Chicken Momo", "Delicious nepali momo", "https://picsum.photos/200/300/?blur.jpg"));

        // Setup RecyclerViews
        setupRecyclerView(view.findViewById(R.id.recycler_recent_recipes), recipes);
        setupRecyclerView(view.findViewById(R.id.recycler_popular_posts), recipes);


        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new GenericRecipeAdapter(recipes));
    }
}
