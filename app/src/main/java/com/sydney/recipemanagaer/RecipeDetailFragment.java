package com.sydney.recipemanagaer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class RecipeDetailFragment extends Fragment {

    private TextView textViewTitle, textViewDescription, textViewIngredients, textViewInstructions, textViewCookingTime;
    private ImageView imageViewRecipe;

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

        return view;
    }
}
