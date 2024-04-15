package com.sydney.recipemanagaer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
public class CreateRecipeFragment extends Fragment {

    private EditText editTextRecipeName, editTextRecipeDescription, editTextIngredients, editTextInstructions;
    private Button buttonSubmitRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        editTextRecipeName = view.findViewById(R.id.editTextRecipeName);
        editTextRecipeDescription = view.findViewById(R.id.editTextRecipeDescription);
        editTextIngredients = view.findViewById(R.id.editTextIngredients);
        editTextInstructions = view.findViewById(R.id.editTextInstructions);
        buttonSubmitRecipe = view.findViewById(R.id.buttonSubmitRecipe);

        buttonSubmitRecipe.setOnClickListener(v -> submitRecipe());

        return view;
    }

    private void submitRecipe() {
        String name = editTextRecipeName.getText().toString();
        String description = editTextRecipeDescription.getText().toString();
        String ingredients = editTextIngredients.getText().toString();
        String instructions = editTextInstructions.getText().toString();

        // Validate input and process the recipe creation
        // Here, you can communicate with ViewModel or directly with your data layer
    }
}
