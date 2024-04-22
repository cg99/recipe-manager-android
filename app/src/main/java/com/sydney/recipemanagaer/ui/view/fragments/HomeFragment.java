package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.utils.Util;
import com.sydney.recipemanagaer.ui.view.adapters.GenericRecipeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements GenericRecipeAdapter.OnRecipeClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = view.findViewById(R.id.search_view);
        ImageButton filterButton = view.findViewById(R.id.button_filter);

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
        recipes.add(new Recipe(
                "Chocolate Brownie",
                "A rich and fudgy chocolate brownie",
                Arrays.asList("Chocolate", "Flour", "Sugar", "Eggs"),
                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
                25,
                "https://picsum.photos/id/221/200.jpg"
        ));

        recipes.add(new Recipe(
                "Caesar Salad",
                "Classic Caesar salad with romaine lettuce, parmesan cheese, and croutons",
                Arrays.asList("Romaine Lettuce", "Parmesan Cheese", "Croutons", "Caesar Dressing"),
                "Toss lettuce with croutons, cheese, and Caesar dressing.",
                10,
                "https://picsum.photos/id/222/200.jpg"
        ));

        recipes.add(new Recipe(
                "Spaghetti Carbonara",
                "Italian pasta dish from Rome made with egg, hard cheese, pancetta, and pepper",
                Arrays.asList("Spaghetti", "Egg", "Hard Cheese", "Pancetta", "Pepper"),
                "Cook spaghetti and mix with eggs, pancetta, and cheese.",
                20,
                "https://picsum.photos/id/223/200.jpg"
        ));

        recipes.add(new Recipe(
                "Tomato Soup",
                "Creamy tomato soup perfect for any season",
                Arrays.asList("Tomatoes", "Cream", "Salt", "Pepper"),
                "Blend boiled tomatoes with cream and simmer, season to taste.",
                30,
                "https://picsum.photos/id/229/200.jpg"
        ));

        recipes.add(new Recipe(
                "Grilled Cheese Sandwich",
                "Simple and classic grilled cheese sandwich",
                Arrays.asList("Bread", "Cheese", "Butter"),
                "Butter the bread, place cheese in between, and grill until golden.",
                15,
                "https://picsum.photos/id/225/200.jpg"
        ));

        // Setup RecyclerViews
        setupRecyclerView(view.findViewById(R.id.recycler_recent_recipes), recipes);
        setupRecyclerView(view.findViewById(R.id.recycler_popular_posts), recipes);


        return view;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Util.handleViewRecipeDetail(recipe, getActivity());
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new GenericRecipeAdapter(recipes, this::onRecipeClick));
    }
}
