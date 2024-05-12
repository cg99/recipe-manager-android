package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.repository.RecipeRepository;
import com.sydney.recipemanagaer.ui.view.adapters.ImageAdapter;
import com.sydney.recipemanagaer.ui.viewmodel.RecipeViewModel;
import com.sydney.recipemanagaer.ui.viewmodel.factory.RecipeViewModelFactory;
import com.sydney.recipemanagaer.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends Fragment {

    private TextView textViewTitle, textViewDescription, textViewIngredients, textViewInstructions, textViewCookingTime;
    private ImageView imageViewRecipe;
    private ImageButton buttonEditRecipe, buttonDeleteRecipe, favoriteButton;
    private RecipeViewModel viewModel;

    private RecyclerView imageRecyclerView;
    private ArrayList<Object> images = new ArrayList<>();
    private ImageAdapter imageAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        RecipeRepository recipeRepository = new RecipeRepository(getContext());
        viewModel = new ViewModelProvider(this, new RecipeViewModelFactory(recipeRepository)).get(RecipeViewModel.class);

        // Initialize UI components
        initializeUI(view);

        // Retrieve and display recipe details
        displayRecipeDetails();

        // Setup button listeners
        setupButtonListeners();

        return view;
    }

    private void initializeUI(View view) {
        textViewTitle = view.findViewById(R.id.textViewRecipeTitle);
        textViewDescription = view.findViewById(R.id.textViewRecipeDescription);
        textViewIngredients = view.findViewById(R.id.textViewRecipeIngredients);
        textViewInstructions = view.findViewById(R.id.textViewRecipeInstructions);
        textViewCookingTime = view.findViewById(R.id.textViewRecipeCookingTime);
        imageViewRecipe = view.findViewById(R.id.imageViewRecipe);
        buttonEditRecipe = view.findViewById(R.id.buttonUpdateRecipe); // Button for editing
        buttonDeleteRecipe = view.findViewById(R.id.buttonDeleteRecipe); // Initialize the Delete Button
        favoriteButton = view.findViewById((R.id.buttonMarkFavorite));

        imageRecyclerView = view.findViewById(R.id.recipeImagesRecyclerView);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        imageAdapter = new ImageAdapter(images); // Assuming 'images' is the list of images
        imageRecyclerView.setAdapter(imageAdapter);

    }

    private void displayRecipeDetails() {
        Bundle args = getArguments();
        if (args != null) {
            textViewTitle.setText(args.getString("title"));
            textViewDescription.setText(args.getString("description"));
            textViewIngredients.setText(args.getString("ingredients"));
            textViewInstructions.setText(args.getString("instructions"));
            textViewCookingTime.setText("Time: " + args.getString("cookingTime") + " minutes");

            // Load the featured image using Glide
            String imageUrl = args.getString("imageUrl");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(Util.getBaseURL() + "recipe/images/" + imageUrl)
                        .placeholder(R.drawable.placeholder_image_background)
                        .error(R.drawable.error_image)
                        .into(imageViewRecipe);
            }

            // Load existing images
            List<String> imageURLs = args.getStringArrayList("imagesUrl");
            if (imageURLs != null && !imageURLs.isEmpty()) {
                for (String url : imageURLs) {
                    images.add(url);
                }
                imageAdapter.notifyDataSetChanged();
            }

        }
    }

    private void setupButtonListeners() {
        buttonEditRecipe.setOnClickListener(v -> navigateToUpdateFragment());
        buttonDeleteRecipe.setOnClickListener(v -> deleteRecipe());

        favoriteButton.setOnClickListener(v -> markRecipeAsFavorite());
    }

    private void navigateToUpdateFragment() {
        Bundle args = getArguments(); // Use existing recipe details
        UpdateRecipeFragment fragment = new UpdateRecipeFragment();
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void deleteRecipe() {
        String recipeId = getArguments().getString("recipeId");
        viewModel.deleteRecipe(recipeId).observe(getViewLifecycleOwner(), result -> {
            if ("Deleted successfully".equals(result)) {
                Toast.makeText(getContext(), "Recipe deleted.", Toast.LENGTH_SHORT).show();
                navigateToHome();
            } else {
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHome() {
        if (isAdded() && getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
            bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        }
    }

    public void markRecipeAsFavorite() {
        {
            String recipeId = getArguments().getString("recipeId");
            viewModel.markAsFavorite(recipeId).observe(getViewLifecycleOwner(), result -> {
                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
