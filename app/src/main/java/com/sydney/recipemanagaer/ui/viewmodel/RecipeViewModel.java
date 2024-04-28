package com.sydney.recipemanagaer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeViewModel(RecipeRepository repository) {
        this.recipeRepository = repository;
    }
    public LiveData<String> createRecipe(Recipe recipe) {
        return recipeRepository.createRecipe(recipe);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public LiveData<String> deleteRecipe(String recipeId) {
        return recipeRepository.deleteRecipe(recipeId);
    }
}
