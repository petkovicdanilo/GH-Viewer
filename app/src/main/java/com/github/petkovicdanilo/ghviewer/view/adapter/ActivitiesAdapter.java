package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;

import java.util.List;

import lombok.Getter;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {

    @Getter
    private List<ActivityDto> activities;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activities_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(activities.get(position).getType().getValue());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public ActivitiesAdapter(List<ActivityDto> activities) {
        this.activities = activities;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        @Getter
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.activity_row_item);
        }
    }
}
