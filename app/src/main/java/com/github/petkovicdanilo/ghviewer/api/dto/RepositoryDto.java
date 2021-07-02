package com.github.petkovicdanilo.ghviewer.api.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
public class RepositoryDto {
    private int id;

    private String nodeId;

    private String name;

    private String fullName;

    private UserDto owner;

    private RepositoryVisibility visibility;

    private String defaultBranch;

    public enum RepositoryVisibility {
        @SerializedName("public")
        PUBLIC,
        @SerializedName("private")
        PRIVATE
    }
}
