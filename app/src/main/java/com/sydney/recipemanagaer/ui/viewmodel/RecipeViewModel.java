package com.sydney.recipemanagaer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    RecipeRepository recipeRepository = new RecipeRepository();

    public LiveData<String> createRecipe(Recipe recipe) {
        // Logic to save recipe to database or server
        return recipeRepository.createRecipe(recipe);
    }
}
