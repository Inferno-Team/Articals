package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BannedUser {
    @SerializedName("id")
    private final int id;
    @SerializedName("cause")
    private final String cause;
    @SerializedName("user")
    private final User user;
    @SerializedName("created_at")
    private final String createdAt;

    public BannedUser(int id, String cause, User user, String createdAt) {
        this.id = id;
        this.cause = cause;
        this.user = user;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getCause() {
        return cause;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
