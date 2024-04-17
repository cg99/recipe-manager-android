package com.sydney.recipemanagaer;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class Util {
    public static void handleViewRecipeDetail(Recipe recipe, Activity activity) {
        // Ensure the activity is a FragmentActivity before attempting to use getSupportFragmentManager
        if (activity instanceof FragmentActivity) {
            RecipeDetailFragment detailFragment = new RecipeDetailFragment();
            Bundle args = new Bundle();
            args.putString("title", recipe.getName());
            args.putString("description", recipe.getDescription());
            args.putString("imageUrl", recipe.getFeaturedImgURL());
            args.putString("instructions", recipe.getInstructions());
            args.putString("cookingTime", "Time: " + recipe.getCookingTime() + " minutes"); // Pass as int if you want to use it as a number later
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


    public static User getUser() {
        return new User("John Doe",
                "john.doe@example.com",
                "Passionate food lover.",
                "https://picsum.photos/id/250/200.jpg");

    }
}