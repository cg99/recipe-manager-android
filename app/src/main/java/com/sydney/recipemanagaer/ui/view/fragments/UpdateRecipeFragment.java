package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;
import com.sydney.recipemanagaer.ui.viewmodel.RecipeViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.RecipeViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateRecipeFragment extends Fragment {
    private EditText editTextRecipeName, editTextRecipeDescription, editTextInstructions, editTextCookingTime;
    private ImageView imageViewSelected;
    private TextView textUpdateRecipeLabel;
    private Button buttonUpdateRecipe;
    private AutoCompleteTextView autoCompleteTextView;
    private ChipGroup chipGroup;
    private RecipeViewModel viewModel;
    private Recipe currentRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        RecipeRepository recipeRepository = new RecipeRepository(getContext());
        viewModel = new ViewModelProvider(this, new RecipeViewModelFactory(recipeRepository)).get(RecipeViewModel.class);

        initViews(view);
        populateFields();
        setListeners();

        return view;
    }

    private void initViews(View view) {
        textUpdateRecipeLabel = view.findViewById(R.id.textLayoutLabel);
        editTextRecipeName = view.findViewById(R.id.editTextRecipeName);
        editTextRecipeDescription = view.findViewById(R.id.editTextRecipeDescription);
        editTextInstructions = view.findViewById(R.id.editTextInstructions);
        editTextCookingTime = view.findViewById(R.id.editTextCookingTime);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        buttonUpdateRecipe = view.findViewById(R.id.buttonSubmitRecipe);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewIngredients);
        chipGroup = view.findViewById(R.id.chipGroupSelectedIngredients);

        textUpdateRecipeLabel.setText("Update Recipe");
        buttonUpdateRecipe.setText("Update Recipe");
    }

    private void populateFields() {
        Bundle args = getArguments();
        if (args != null) {
            editTextRecipeName.setText(args.getString("title"));
            editTextRecipeDescription.setText(args.getString("description"));
            editTextInstructions.setText(args.getString("instructions"));
            editTextCookingTime.setText(args.getString("cookingTime"));
            loadImage(args.getString("imageUrl"));

            // Split the ingredients string into an array and load as chips
            String ingredients = args.getString("ingredients");
            if (ingredients != null && !ingredients.isEmpty()) {
                String[] ingredientArray = ingredients.split(", ");
                loadIngredientsAsChips(Arrays.asList(ingredientArray));
            }
        }
    }

    private void loadIngredientsAsChips(List<String> ingredients) {
        for (String ingredient : ingredients) {
            addChipToGroup(ingredient);
        }
    }

    private void addChipToGroup(String ingredient) {
        Chip chip = new Chip(getContext());
        chip.setText(ingredient);
//        chip.setChipIconResource(R.drawable.ic_ingredient);
//        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));
        chipGroup.addView(chip);
    }

    private void loadImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image_background)
                    .error(R.drawable.error_image)
                    .into(imageViewSelected);
        }
    }

    private void setListeners() {
        buttonUpdateRecipe.setOnClickListener(v -> updateRecipe());
        // Additional listeners
    }


    private void updateRecipe() {
        String title = editTextRecipeName.getText().toString();
        String description = editTextRecipeDescription.getText().toString();
        String instructions = editTextInstructions.getText().toString();
        String cookingTimeStr = editTextCookingTime.getText().toString();
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            ingredients.add(chip.getText().toString());
        }
        int cookingTime;
        try {
            cookingTime = Integer.parseInt(cookingTimeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid cooking time. Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        Recipe updatedRecipe = new Recipe(title, description, ingredients, instructions, cookingTime, currentRecipe.getFeaturedImgURL());

//        viewModel.updateRecipe(updatedRecipe).observe(getViewLifecycleOwner(), result -> {
//            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
//        });
    }
}
