package com.github.petkovicdanilo.ghviewer.api.dto.git;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class TreeDto {
    private String sha;

    private List<TreeItem> tree;

    @Getter
    public class TreeItem {
        private String sha;
        private String path;
        private TreeItemType type;
    }

    public enum TreeItemType {
        @SerializedName("tree")
        TREE,
        @SerializedName("blob")
        BLOB
    }
}
