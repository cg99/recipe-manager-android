package com.sydney.recipemanagaer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment for creating new recipes. Includes fields for inputting recipe details and a picker for a featured image.
 */
public class CreateRecipeFragment extends Fragment {

    // UI components for entering recipe details
    private EditText editTextRecipeName, editTextRecipeDescription, editTextInstructions, editTextCookingTime;
    private ImageView imageViewSelected;
    private Button buttonSubmitRecipe;

    // Stores the URI of the selected image
    private Uri imageUri;

    // Launcher for image selection from the gallery
    private ActivityResultLauncher<String> imagePickerLauncher;

    // AutoCompleteTextView for ingredient input and ChipGroup for displaying selected ingredients
    private AutoCompleteTextView autoCompleteTextView;
    private ChipGroup chipGroup;
    private List<String> ingredients;
    private IngredientAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the image picker with a callback for when an image is selected
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    imageUri = uri;  // Store the selected image URI
                    imageViewSelected.setImageURI(uri);  // Display the selected image
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        initViews(view);  // Initialize UI components
        setListeners(view);  // Setup event listeners
        return view;
    }

    /**
     * Initializes UI components from the layout.
     */
    private void initViews(View view) {
        editTextRecipeName = view.findViewById(R.id.editTextRecipeName);
        editTextRecipeDescription = view.findViewById(R.id.editTextRecipeDescription);

        // Initialize the list of ingredients and the adapter for AutoCompleteTextView
        ingredients = new ArrayList<>(Arrays.asList("Flour", "Sugar", "Eggs", "Butter", "Milk", "Salt"));
        adapter = new IngredientAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, ingredients);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewIngredients);
        chipGroup = view.findViewById(R.id.chipGroupSelectedIngredients);
        autoCompleteTextView.setAdapter(adapter);

        editTextInstructions = view.findViewById(R.id.editTextInstructions);
        editTextCookingTime = view.findViewById(R.id.editTextCookingTime);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        buttonSubmitRecipe = view.findViewById(R.id.buttonSubmitRecipe);
    }

    /**
     * Sets up listeners for various interactive components like buttons and AutoCompleteTextView.
     */
    private void setListeners(View view) {
        view.findViewById(R.id.buttonSelectImage).setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");  // Open the image picker
        });

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String selection = parent.getItemAtPosition(position).toString();
            if (selection.startsWith("Add new: ")) {
                String ingredient = selection.substring(9);
                if (!ingredients.contains(ingredient)) {
                    ingredients.add(ingredient);
                }
                selection = ingredient;
            }
            addChipToGroup(selection);
            autoCompleteTextView.setText("");
        });

        buttonSubmitRecipe.setOnClickListener(v -> submitRecipe());  // Set listener for the submit button
    }

    /**
     * Adds a new chip to the ChipGroup for each selected ingredient.
     */
    private void addChipToGroup(String ingredient) {
        Chip chip = new Chip(getContext());
        chip.setText(ingredient);
        chip.setChipIconResource(R.drawable.ic_check);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));  // Remove chip on close icon click
        chipGroup.addView(chip);  // Add the chip to the ChipGroup
    }

    /**
     * Collects the input data from the fields and handles the creation of a new recipe.
     */
    private void submitRecipe() {
        String name = editTextRecipeName.getText().toString();
        String description = editTextRecipeDescription.getText().toString();
//        String ingredients = editTextIngredients.getText().toString();
        String instructions = editTextInstructions.getText().toString();
        String cookingTime = editTextCookingTime.getText().toString();

        // Placeholder for recipe submission logic
    }
}
