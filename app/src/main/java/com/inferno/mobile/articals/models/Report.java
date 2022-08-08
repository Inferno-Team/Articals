package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Report {
    @SerializedName("id")
    private final int id;
    @SerializedName("reporter_id")
    private final int reporterId;
    @SerializedName("cause")
    private final String cause;
    @SerializedName("reporter")
    private final User reporter;
    @SerializedName("comment")
    private final Comment comment;
    @SerializedName("created_at")
    private final Date createdAt;

    public Report(int id, int reporterId, String cause, User reporter, Comment comment, Date createdAt) {
        this.id = id;
        this.reporterId = reporterId;
        this.cause = cause;
        this.reporter = reporter;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getReporterId() {
        return reporterId;
    }

    public String getCause() {
        return cause;
    }

    public User getReporter() {
        return reporter;
    }

    public Comment getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
