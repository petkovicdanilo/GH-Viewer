package com.github.petkovicdanilo.ghviewer.api.dto;

import lombok.Getter;

@Getter()
public class UserDto {
    private String login;

    private int id;

    private String nodeId;

    private String avatarUrl;

    private String gravatarId;

    private String url;

    private String htmlUrl;

    private String followersUrl;

    private String followingUrl;

    private String gistsUrl;

    private String starredUrl;

    private String subscriptionsUrl;

    private String organizationsUrl;

    private String reposUrl;

    private String eventsUrl;

    private String receivedEventsUrl;

    private String type;

    private boolean siteAdmin;
}

