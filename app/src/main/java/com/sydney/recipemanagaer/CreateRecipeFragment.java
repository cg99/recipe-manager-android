package com.sydney.recipemanagaer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

/**
 * Fragment to handle the creation of new recipes, including input fields for
 * recipe details and an image picker for selecting a featured image.
 */
public class CreateRecipeFragment extends Fragment {

    // UI Components for recipe data input
    private EditText editTextRecipeName, editTextRecipeDescription;
    private EditText editTextIngredients, editTextInstructions, editTextCookingTime;
    private ImageView imageViewSelected;
    private Button buttonSubmitRecipe;

    // URI for the selected image
    private Uri imageUri;

    // Launcher for selecting an image from the gallery
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the image picker launcher with a callback to handle the selected image
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    imageUri = uri;
                    imageViewSelected.setImageURI(uri);
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        // Initialize all view components
        initViews(view);

        // Set listeners for buttons
        setListeners(view);

        return view;
    }

    /**
     * Initializes views and links them to their respective UI components.
     */
    private void initViews(View view) {
        editTextRecipeName = view.findViewById(R.id.editTextRecipeName);
        editTextRecipeDescription = view.findViewById(R.id.editTextRecipeDescription);
        editTextIngredients = view.findViewById(R.id.editTextIngredients);
        editTextInstructions = view.findViewById(R.id.editTextInstructions);
        editTextCookingTime = view.findViewById(R.id.editTextCookingTime);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        buttonSubmitRecipe = view.findViewById(R.id.buttonSubmitRecipe);
    }

    /**
     * Sets listeners for interactive components like buttons.
     */
    private void setListeners(View view) {
        view.findViewById(R.id.buttonSelectImage).setOnClickListener(v -> {
            // Open the image picker when the button is clicked
            imagePickerLauncher.launch("image/*");
        });

        buttonSubmitRecipe.setOnClickListener(v -> submitRecipe());
    }

    /**
     * Collects input data and handles the creation of a new recipe.
     */
    private void submitRecipe() {
        String name = editTextRecipeName.getText().toString();
        String description = editTextRecipeDescription.getText().toString();
        String ingredients = editTextIngredients.getText().toString();
        String instructions = editTextInstructions.getText().toString();
        String cookingTime = editTextCookingTime.getText().toString();

        // Validate input and process the recipe creation
        // Here, you can communicate with ViewModel or directly with your data layer
    }
}
