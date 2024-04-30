package com.sydney.recipemanagaer.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Recipe;
import com.sydney.recipemanagaer.ui.view.fragments.RecipeDetailFragment;
import com.sydney.recipemanagaer.ui.view.fragments.UpdateRecipeFragment;

public class Util {
    public static final String SHARED_PREFS_FILE = "appPreferences";
    public static final String TOKEN_KEY = "sessionToken";
    public static final String USER_ID_KEY = "userID";

    public static void handleViewRecipeDetail(Recipe recipe, Activity activity) {
        // Ensure the activity is a FragmentActivity before attempting to use getSupportFragmentManager
        if (activity instanceof FragmentActivity) {
            RecipeDetailFragment detailFragment = new RecipeDetailFragment();
            Bundle args = new Bundle();
            args.putString("recipeId", recipe.getRecipeId());
            args.putString("title", recipe.getTitle());
            args.putString("description", recipe.getDescription());
            args.putString("ingredients", String.join(", ", recipe.getIngredients())); // Joining the list of ingredients into a single string
            args.putString("imageUrl", recipe.getFeaturedImgURL());
            args.putString("instructions", recipe.getInstructions());
            args.putString("cookingTime", recipe.getCookingTime()+""); // Pass as int if you want to use it as a number later
            detailFragment.setArguments(args);

            // Perform the fragment transaction to display the RecipeDetailFragment
            ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // Log or handle the case where the activity is not a FragmentActivity
            System.out.println("Error: Activity provided is not a FragmentActivity and cannot perform fragment transactions.");
        }
    }

    public static void loadImage(UpdateRecipeFragment context, String imageUrl, ImageView imageViewSelected) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image_background)
                    .error(R.drawable.error_image)
                    .into(imageViewSelected);
        }
    }
}
