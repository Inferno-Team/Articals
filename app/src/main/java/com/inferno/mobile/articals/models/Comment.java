package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private final int id;
    @SerializedName("comment")
    private final String comment;
    @SerializedName("user")
    private final User user;
    @SerializedName("time")
    private final String at;

    public Comment(int id, String comment, User user, String at) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.at = at;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public String getAt() {
        return at;
    }
}
