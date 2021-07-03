package com.github.petkovicdanilo.ghviewer.api.dto.git;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TreeDto {
    private String sha;

    private List<TreeItem> tree;

    @Getter
    @AllArgsConstructor
    public static class TreeItem {
        private String sha;
        private String path;
        private TreeItemType type;

        public static TreeItem parentTree() {
            return new TreeItem("", "..", TreeItemType.TREE);
        }
    }

    public enum TreeItemType {
        @SerializedName("tree")
        TREE,
        @SerializedName("blob")
        BLOB
    }
}
