package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;

import java.util.List;

import lombok.Getter;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    @Getter
    private List<RepositoryDto> repositories;

    private OnRepositoryListener onRepositoryListener;

    private Context context;

    public RepositoriesAdapter(List<RepositoryDto> repositories, Context context,
                               OnRepositoryListener onRepositoryListener) {
        this.repositories = repositories;
        this.onRepositoryListener = onRepositoryListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false);

        return new ViewHolder(view, onRepositoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(repositories.get(position).getFullName());

        ImageView ownerAvatar = holder.getOwnerAvatar();
        RepositoryDto repository = repositories.get(position);
        Glide.with(context)
                .load(repository.getOwner().getAvatarUrl())
                .circleCrop()
                .into(ownerAvatar);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Getter
        private final TextView textView;

        @Getter
        private final ImageView ownerAvatar;

        private OnRepositoryListener listener;

        public ViewHolder(View view, OnRepositoryListener listener) {
            super(view);

            textView = view.findViewById(R.id.repository_search_item_name);
            ownerAvatar = view.findViewById(R.id.repository_owner_avatar);

            this.listener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRepositoryClick(getAdapterPosition());
        }
    }

    public interface OnRepositoryListener {
        void onRepositoryClick(int position);
    }
}
