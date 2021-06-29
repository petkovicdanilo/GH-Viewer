package com.github.petkovicdanilo.ghviewer.api.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

@Getter()
@Builder()
public class CreateRepositoryDto {
    private String name;

    @SerializedName("private")
    private boolean privateRepo;
}
