package com.sydney.recipemanagaer.ui.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sydney.recipemanagaer.R;
import com.sydney.recipemanagaer.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnUserListener onUserListener;

    public interface OnUserListener {
        void onEditUser(User user);
        void onDeleteUser(String userId);
    }
    public UserAdapter(List<User> userList, OnUserListener onUserListener) {
        this.userList = userList;
        this.onUserListener = onUserListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.usernameTextView.setText(user.getUsername());
        holder.bioTextView.setText(user.getBio());
        Glide.with(holder.itemView.getContext()).load(user.getProfilePic()).into(holder.profileImageView);

        holder.editButton.setOnClickListener(v -> onUserListener.onEditUser(user));
        holder.deleteButton.setOnClickListener(v -> onUserListener.onDeleteUser(user.getEmail()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, usernameTextView, bioTextView;
        ImageView profileImageView;
        Button editButton, deleteButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            bioTextView = itemView.findViewById(R.id.bioTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            editButton = itemView.findViewById(R.id.btnEditUser);
            deleteButton = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}
