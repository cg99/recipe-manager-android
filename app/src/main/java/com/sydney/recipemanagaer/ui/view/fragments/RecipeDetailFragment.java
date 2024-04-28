package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;
import com.sydney.recipemanagaer.ui.viewmodel.RecipeViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.RecipeViewModelFactory;

public class RecipeDetailFragment extends Fragment {

    private TextView textViewTitle, textViewDescription, textViewIngredients, textViewInstructions, textViewCookingTime;
    private ImageView imageViewRecipe;
    private Button buttonDeleteRecipe;
    private RecipeViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Initialize UI components
        textViewTitle = view.findViewById(R.id.textViewRecipeTitle);
        textViewDescription = view.findViewById(R.id.textViewRecipeDescription);
        textViewIngredients = view.findViewById(R.id.textViewRecipeIngredients);
        imageViewRecipe = view.findViewById(R.id.imageViewRecipe);
        textViewInstructions = view.findViewById(R.id.textViewRecipeInstructions);
        textViewCookingTime = view.findViewById(R.id.textViewRecipeCookingTime);
        buttonDeleteRecipe = view.findViewById(R.id.buttonDeleteRecipe); // Initialize the Button

        // Retrieve recipe details passed via fragment arguments
        Bundle args = getArguments();
        if (args != null) {
            textViewTitle.setText(args.getString("title"));
            textViewDescription.setText(args.getString("description"));
            textViewIngredients.setText(args.getString("ingredients"));  // Displaying the ingredients
            textViewInstructions.setText(args.getString("instructions"));
            textViewCookingTime.setText(args.getString("cookingTime"));

            // Load the featured image using Glide
            String imageUrl = args.getString("imageUrl");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image_background)  // Placeholder image
                        .error(R.drawable.error_image)  // Image to show on load failure
                        .into(imageViewRecipe);
            }
        }

        RecipeRepository recipeRepository = new RecipeRepository(getContext());
        viewModel = new ViewModelProvider(this, new RecipeViewModelFactory(recipeRepository)).get(RecipeViewModel.class);
        buttonDeleteRecipe.setOnClickListener(view1 -> viewModel.deleteRecipe("66223c87bf0e44b6224be3cd").observe(getActivity(), result -> {
            if (result.equals("Deleted successfully")) {
                Toast.makeText(getContext(), "Recipe deleted.", Toast.LENGTH_SHORT).show();

                // navigate to home
                navigateToHome();
            } else {
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        }));


        return view;
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
