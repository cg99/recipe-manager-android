package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.utils.Util;
import com.sydney.recipemanagaer.ui.view.adapters.FavoriteAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.FavoriteActionsListener {
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Chocolate Brownie",
                "A rich and fudgy chocolate brownie",
                Arrays.asList("Bread", "Cheese", "Butter"),
                "Mix melted chocolate with flour and sugar, add eggs, and bake for 25 minutes.",
                25,
                "https://picsum.photos/id/221/200.jpg"));
        recipes.add(new Recipe("Caesar Salad",
                "Classic Caesar salad with romaine lettuce, parmesan cheese, and croutons",
                Arrays.asList("Bread", "Cheese", "Butter"),
                "Toss lettuce with croutons, cheese, and Caesar dressing.",
                10,
                "https://picsum.photos/id/222/200.jpg"));

        adapter = new FavoriteAdapter(getContext(), recipes, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRemoveFavorite(Recipe recipe) {
        Toast.makeText(getContext(), "Removed from Favorites: " + recipe.getName(), Toast.LENGTH_SHORT).show();
        // Optionally update backend or local database
    }

    @Override
    public void onFavoriteRecipeClick(Recipe recipe) {
        Util.handleViewRecipeDetail(recipe, getActivity());
    }
}
