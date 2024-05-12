package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;
import com.sydney.recipemanagaer.ui.view.adapters.FavoriteAdapter;
import com.sydney.recipemanagaer.ui.viewmodel.RecipeViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.RecipeViewModelFactory;
import com.sydney.recipemanagaer.utils.Util;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.FavoriteActionsListener {
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private RecipeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecipeRepository recipeRepository = new RecipeRepository(getContext());
        viewModel = new ViewModelProvider(this, new RecipeViewModelFactory(recipeRepository)).get(RecipeViewModel.class);

        viewModel.getUserFavorites().observe(getViewLifecycleOwner(), recipes -> {
            if(!recipes.isEmpty()){
                view.findViewById(R.id.empty_view).setVisibility(View.GONE);
                adapter = new FavoriteAdapter(getContext(), recipes, this);
                recyclerView.setAdapter(adapter);
            }else{
                view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            }

        });

        return view;
    }

    @Override
    public void onRemoveFavorite(Recipe recipe) {
        viewModel.markAsFavorite(recipe.getRecipeId()).observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onFavoriteRecipeClick(Recipe recipe) {
        Util.handleViewRecipeDetail(recipe, getActivity());
    }
}
