package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.git.TreeDto;

import java.util.List;

import lombok.Getter;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {

    @Getter
    private List<TreeDto.TreeItem> treeItems;

    public TreeAdapter(List<TreeDto.TreeItem> treeItems) {
        this.treeItems = treeItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tree_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TreeDto.TreeItem treeItem = treeItems.get(position);

        holder.getTreeItemName().setText(treeItem.getPath());
    }

    @Override
    public int getItemCount() {
        return treeItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Getter
        private TextView treeItemName;

        public ViewHolder(View view) {
            super(view);

            treeItemName = view.findViewById(R.id.tree_item_name);
        }
    }
}
