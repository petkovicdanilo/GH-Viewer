package com.github.petkovicdanilo.ghviewer.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter()
public class ActivityDto {

    private String id;

    private ActivityType type;

    private ActorDto actor;

    private RepositoryDto repo;

    private HashMap<String, Object> payload;

    @AllArgsConstructor
    public enum ActivityType {
        @SerializedName("CommitCommentEvent")
        COMMIT_COMMENT_EVENT("CommitCommentEvent"),
        @SerializedName("CreateEvent")
        CREATE_EVENT("CreateEvent"),
        @SerializedName("DeleteEvent")
        DELETE_EVENT("DeleteEvent"),
        @SerializedName("ForkEvent")
        FORK_EVENT("ForkEvent"),
        @SerializedName("GollumEvent")
        GOLLUM_EVENT("GollumEvent"),
        @SerializedName("IssueCommentEvent")
        ISSUE_COMMENT_EVENT("IssueCommentEvent"),
        @SerializedName("IssuesEvent")
        ISSUES_EVENT("IssuesEvent"),
        @SerializedName("MemberEvent")
        MEMBER_EVENT("MemberEvent"),
        @SerializedName("PublicEvent")
        PUBLIC_EVENT("PublicEvent"),
        @SerializedName("PullRequestEvent")
        PULL_REQUEST_EVENT("PullRequestEvent"),
        @SerializedName("PullRequestReviewEvent")
        PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent"),
        @SerializedName("PullRequestReviewCommentEvent")
        PULL_REQUEST_REVIEW_COMMENT_EVENT("PullRequestReviewCommentEvent"),
        @SerializedName("PushEvent")
        PUSH_EVENT("PushEvent"),
        @SerializedName("ReleaseEvent")
        RELEASE_EVENT("ReleaseEvent"),
        @SerializedName("SponsorshipEvent")
        SPONSORSHIP_EVENT("SponsorshipEvent"),
        @SerializedName("WatchEvent")
        WATCH_EVENT("WatchEvent");

        @Getter()
        private String value;

    };

    @Getter()
    public class RepositoryDto {
        private int id;
        private String name;
    }

    @Getter()
    public class ActorDto {
        private int id;
        private String login;
        private String avatarUrl;
    }

}
