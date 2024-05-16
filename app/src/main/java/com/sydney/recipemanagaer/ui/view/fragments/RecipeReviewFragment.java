package com.sydney.recipemanagaer.ui.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.Review;
import com.sydney.recipemanagaer.ui.view.adapters.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeReviewFragment extends Fragment{
    private RecyclerView reviewsRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_review, container, false);

        // Initialize RecyclerView and reviews list
        reviewsRecyclerView = rootView.findViewById(R.id.reviewsRecyclerView);
        reviews = getReviews(); // Implement this method to fetch reviews

        // Create and set adapter
        reviewAdapter = new ReviewAdapter(reviews);
        reviewsRecyclerView.setAdapter(reviewAdapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    // Method to fetch dummy reviews (replace with your implementation)
    private List<Review> getReviews() {
        List<Review> dummyReviews = new ArrayList<>();
        // Add dummy reviews
        dummyReviews.add(new Review("User1", 4.5f, "Great recipe! Loved it."));
        dummyReviews.add(new Review("User2", 2.5f, "Could be better, but still good."));
        dummyReviews.add(new Review("User3", 5.0f, "Wow! Loved the recipe."));
        dummyReviews.add(new Review("User4", 5.0f, "Amazing! Will definitely make again."));
        dummyReviews.add(new Review("User5", 5.0f, "Very Nice! Will definitely make again."));
        dummyReviews.add(new Review("User6", 3.0f, "We Will definitely make again."));
        return dummyReviews;
    }
}
