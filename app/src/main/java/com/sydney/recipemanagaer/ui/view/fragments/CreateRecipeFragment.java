package com.sydney.recipemanagaer.ui.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;
import com.sydney.recipemanagaer.ui.view.adapters.IngredientAdapter;
import com.sydney.recipemanagaer.ui.viewmodel.RecipeViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.RecipeViewModelFactory;

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
    private RecipeViewModel viewModel;

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
        RecipeRepository recipeRepository = new RecipeRepository(getContext());
        viewModel = new ViewModelProvider(this, new RecipeViewModelFactory(recipeRepository)).get(RecipeViewModel.class);
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
        ingredients = new ArrayList<>(Arrays.asList(
                "Flour", "Sugar", "Eggs", "Butter", "Milk", "Salt", "Tomatoes", "Onions",
                "Carrots", "Bell peppers", "Garlic", "Broccoli", "Spinach", "Potatoes",
                "Apples", "Bananas", "Oranges", "Strawberries", "Grapes", "Lemons",
                "Pineapple", "Chicken", "Beef", "Pork", "Lamb", "Turkey", "Salmon",
                "Tuna", "Shrimp", "Cod", "Crab", "Lobster", "Cheese", "Yogurt", "Cream",
                "Rice", "Wheat flour", "Oats", "Quinoa", "Lentils", "Chickpeas",
                "Black beans", "Almonds", "Walnuts", "Peanuts", "Sunflower seeds",
                "Flaxseeds", "Basil", "Oregano", "Thyme", "Rosemary", "Cumin", "Paprika",
                "Black pepper", "Olive oil", "Canola oil", "Coconut oil", "Vegetable oil",
                "Ghee", "Honey", "Maple syrup", "Molasses", "Kale", "Celery", "Zucchini",
                "Eggplant", "Peas", "Cauliflower", "Asparagus", "Artichoke", "Mushrooms",
                "Pomegranate", "Blueberries", "Raspberries", "Mango", "Avocado", "Peach",
                "Cabbage", "Lettuce", "Cucumber", "Corn", "Radishes", "Mint", "Cilantro",
                "Parsley", "Fennel", "Chili powder", "Turmeric", "Cinnamon", "Nutmeg",
                "Cardamom", "Vanilla", "Sesame seeds", "Pistachios", "Hazelnuts", "Pumpkin seeds",
                "Macadamia nuts"
        ));
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

        // ingredients add
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
        String title = editTextRecipeName.getText().toString().trim();
        String description = editTextRecipeDescription.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();
        String cookingTimeStr = editTextCookingTime.getText().toString().trim();

        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            ingredients.add(chip.getText().toString());
        }
        // Basic validation to check if any field is empty
        if (title.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || cookingTimeStr.isEmpty()) {
            // Show an error message or toast notification to the user
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int cookingTime;
        try {
            cookingTime = Integer.parseInt(cookingTimeStr);  // Convert cooking time to integer
        } catch (NumberFormatException e) {
            // Handle case where cooking time is not a valid integer
            Toast.makeText(getContext(), "Invalid cooking time. Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // If all validations are passed, proceed to use the data
        viewModel.createRecipe(new Recipe(
                title, description, ingredients,
                instructions, cookingTime, "https://picsum.photos/id/236/200.jpg"
        )).observe(getViewLifecycleOwner(), result -> {
            if ("Recipe created successfully!".equals(result)) {
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                clearRecipeForm();
                navigateToHome();  // Navigate to the HomeFragment after submission
            } else {
                Toast.makeText(getContext(), "Failed to create recipe.", Toast.LENGTH_LONG).show();
            }
        });

        // Optionally, clear the form or navigate away
        clearRecipeForm();
        navigateToHome();
    }

    private void clearRecipeForm() {
        editTextRecipeName.setText("");
        editTextRecipeDescription.setText("");
        editTextInstructions.setText("");
        editTextCookingTime.setText("");
        autoCompleteTextView.setText("");
        chipGroup.removeAllViews();  // Remove all chips from the ChipGroup
        imageViewSelected.setImageResource(android.R.color.transparent);  // Reset or remove the image
        imageUri = null;  // Clear the stored URI
    }

    private void navigateToHome() {
        // Check if fragment is attached to the activity
        if (isAdded() && getActivity() != null) {
            // Use the FragmentManager to start a new transaction and replace the current fragment with the HomeFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Assuming your BottomNavigationView's ID is nav_view and is part of your Activity layout
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);  // Set the Home item as selected

    }




}
