package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {

    @Getter
    private List<ActivityDto> activities;

    private Fragment fragment;

    private OnActivityListener onActivityListener;

    public ActivitiesAdapter(List<ActivityDto> activities, Fragment fragment,
                             OnActivityListener onActivityListener) {
        this.activities = activities;
        this.fragment = fragment;
        this.onActivityListener = onActivityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activities_row_item, parent, false);

        view.setOnClickListener(v -> {

        });

        return new ViewHolder(view, onActivityListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityDto activity = activities.get(position);

        holder.getTextView().setText(activity.getType().getValue());

        ImageView profileImage = holder.getProfileImage();
        Glide.with(fragment)
                .load(activity.getActor().getAvatarUrl())
                .circleCrop()
                .fitCenter()
                .into(profileImage);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Getter
        private final TextView textView;

        @Getter
        private final ImageView profileImage;

        private OnActivityListener listener;

        public ViewHolder(View view, OnActivityListener listener) {
            super(view);

            textView = view.findViewById(R.id.activity_row_item);
            profileImage = view.findViewById(R.id.activity_profile_image);

            this.listener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onActivityClick(getAdapterPosition());
        }
    }

    public interface OnActivityListener {
        void onActivityClick(int position);
    }
}
