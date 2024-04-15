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

public class HomeFragment extends Fragment implements GenericRecipeAdapter.OnRecipeClickListener {

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
        recipes.add(new Recipe(
                "Chocolate Brownie",
                "A rich and fudgy chocolate brownie",
                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
                25,
                "https://picsum.photos/id/221/200.jpg"
        ));

        recipes.add(new Recipe(
                "Caesar Salad",
                "Classic Caesar salad with romaine lettuce, parmesan cheese, and croutons",
                "Toss lettuce with croutons, cheese, and Caesar dressing.",
                10,
                "https://picsum.photos/id/222/200.jpg"
        ));

        recipes.add(new Recipe(
                "Spaghetti Carbonara",
                "Italian pasta dish from Rome made with egg, hard cheese, pancetta, and pepper",
                "Cook spaghetti and mix with eggs, pancetta, and cheese.",
                20,
                "https://picsum.photos/id/223/200.jpg"
        ));

        recipes.add(new Recipe(
                "Tomato Soup",
                "Creamy tomato soup perfect for any season",
                "Blend boiled tomatoes with cream and simmer, season to taste.",
                30,
                "https://picsum.photos/id/229/200.jpg"
        ));

        recipes.add(new Recipe(
                "Grilled Cheese Sandwich",
                "Simple and classic grilled cheese sandwich",
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
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", recipe.getName());
        args.putString("description", recipe.getDescription());
        args.putString("imageUrl", recipe.getFeaturedImgURL());
        args.putString("instructions", recipe.getInstructions());
        args.putString("cookingTime", "Cooking Time: " + recipe.getCookingTime() + " minutes");
        detailFragment.setArguments(args);

        // Perform the fragment transaction to display the RecipeDetailFragment
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new GenericRecipeAdapter(recipes, this::onRecipeClick));
    }
}
