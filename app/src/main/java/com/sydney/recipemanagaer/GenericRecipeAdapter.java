package com.sydney.recipemanagaer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GenericRecipeAdapter extends RecyclerView.Adapter<GenericRecipeAdapter.ViewHolder> {

    private List<Recipe> dataList;

    public GenericRecipeAdapter(List<Recipe> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = dataList.get(position);
        holder.nameTextView.setText(recipe.getName());
        holder.descriptionTextView.setText(recipe.getDescription());


        // Use Glide to load the image asynchronously
        Glide.with(holder.itemView.getContext())
                .load(recipe.getFeaturedImgURL())
                .placeholder(R.drawable.placeholder_image_foreground) // A placeholder image to show until the real image is loaded
                .error(R.drawable.error_image) // An error image to show if the real image fails to load
                .into(holder.featuredImageView); // The target ImageView to load the image into

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ImageView featuredImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            featuredImageView = itemView.findViewById(R.id.featuredImageView);
        }
    }
}

