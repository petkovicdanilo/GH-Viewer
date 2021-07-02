package com.github.petkovicdanilo.ghviewer.api.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private String login;

    private int id;

    private String name;

    private String avatarUrl;

    private String gravatarId;

    private String htmlUrl;

    private String type;
}

