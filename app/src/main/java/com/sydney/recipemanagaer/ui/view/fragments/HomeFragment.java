package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.ui.view.adapters.GenericRecipeAdapter;
import com.sydney.recipemanagaer.ui.viewmodel.HomeViewModel;
import com.sydney.recipemanagaer.utils.Util;

import java.util.List;

public class HomeFragment extends Fragment implements GenericRecipeAdapter.OnRecipeClickListener {
    private RecyclerView recyclerRecentRecipes;
    private RecyclerView recyclerPopularPosts;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        recyclerRecentRecipes = view.findViewById(R.id.recycler_recent_recipes);
        recyclerPopularPosts = view.findViewById(R.id.recycler_popular_posts);

        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            setupRecyclerView(recyclerRecentRecipes, recipes);
            setupRecyclerView(recyclerPopularPosts, recipes);
        });

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search submit action
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform live filtering action
                return true;
            }
        });

        ImageButton filterButton = view.findViewById(R.id.button_filter);
        filterButton.setOnClickListener(v -> {
            // Handle filter button click action
        });

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new GenericRecipeAdapter(recipes, this));
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Util.handleViewRecipeDetail(recipe, getActivity());
    }
}
