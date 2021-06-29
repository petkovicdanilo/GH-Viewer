package com.github.petkovicdanilo.ghviewer.api.dto;

import lombok.Getter;

@Getter()
public class RepositoryDto {
    private int id;

    private String nodeId;

    private String name;

    private String fullName;

    private UserDto owner;
}
