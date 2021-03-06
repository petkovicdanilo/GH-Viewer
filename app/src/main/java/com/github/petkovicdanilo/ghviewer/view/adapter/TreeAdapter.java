package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private OnTreeItemListener onTreeItemListener;

    public TreeAdapter(List<TreeDto.TreeItem> treeItems, OnTreeItemListener onTreeItemListener) {
        this.treeItems = treeItems;
        this.onTreeItemListener = onTreeItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tree_item, parent, false);

        return new ViewHolder(view, onTreeItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TreeDto.TreeItem treeItem = treeItems.get(position);

        holder.getTreeItemName().setText(treeItem.getPath());

        if(treeItem.getType() == TreeDto.TreeItemType.TREE) {
            holder.getIcon().setImageResource(R.drawable.ic_baseline_folder_24);
        }
        else {
            holder.getIcon().setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }
    }

    @Override
    public int getItemCount() {
        return treeItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Getter
        private TextView treeItemName;

        @Getter
        private ImageView icon;

        private OnTreeItemListener listener;

        public ViewHolder(View view, OnTreeItemListener listener) {
            super(view);

            this.listener = listener;

            treeItemName = view.findViewById(R.id.tree_item_name);
            icon = view.findViewById(R.id.tree_icon);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onTreeItemClicked(getAdapterPosition());
        }
    }

    public interface OnTreeItemListener {
        void onTreeItemClicked(int position);
    }
}
