package com.sydney.recipemanagaer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class RecipeDetailFragment extends Fragment {

    private TextView textViewTitle, textViewDescription, textViewInstructions, textViewCookingTime;
    private ImageView imageViewRecipe;

    private OnBackPressedCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        textViewTitle = view.findViewById(R.id.textViewRecipeTitle);
        textViewDescription = view.findViewById(R.id.textViewRecipeDescription);
        imageViewRecipe = view.findViewById(R.id.imageViewRecipe);
        textViewInstructions = view.findViewById(R.id.textViewRecipeInstructions);
        textViewCookingTime = view.findViewById(R.id.textViewRecipeCookingTime);

        // recipe details are passed via arguments
        if (getArguments() != null) {
            textViewTitle.setText(getArguments().getString("title"));
            textViewDescription.setText(getArguments().getString("description"));
            textViewInstructions.setText(getArguments().getString("instructions"));
            textViewCookingTime.setText(getArguments().getString("cookingTime"));

            // featured image
            String imageUrl = getArguments().getString("imageUrl");
            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image_background)  // Optional: default image until the actual image loads
                        .error(R.drawable.error_image)  // Optional: image to show on error
                        .into(imageViewRecipe);
            }

        }


        return view;
    }
}
