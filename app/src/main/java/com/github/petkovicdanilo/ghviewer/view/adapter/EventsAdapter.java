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
import com.github.petkovicdanilo.ghviewer.api.dto.EventDto;

import java.util.List;

import lombok.Getter;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    @Getter
    private List<EventDto> events;

    private Fragment fragment;

    private OnEventListener onEventListener;

    public EventsAdapter(List<EventDto> events, Fragment fragment,
                         OnEventListener onEventListener) {
        this.events = events;
        this.fragment = fragment;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new ViewHolder(view, onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventDto event = events.get(position);

        holder.getTextView().setText(event.getType().getValue());

        ImageView profileImage = holder.getProfileImage();
        Glide.with(fragment)
                .load(event.getActor().getAvatarUrl())
                .circleCrop()
                .fitCenter()
                .into(profileImage);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Getter
        private final TextView textView;

        @Getter
        private final ImageView profileImage;

        private OnEventListener listener;

        public ViewHolder(View view, OnEventListener listener) {
            super(view);

            textView = view.findViewById(R.id.event_row_item);
            profileImage = view.findViewById(R.id.event_profile_image);

            this.listener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener {
        void onEventClick(int position);
    }
}
