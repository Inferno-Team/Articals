package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Article {
    @SerializedName("id")
    private final Integer id;

    @SerializedName("name")
    private final String name;

    @SerializedName("type")
    private final String type;

    @SerializedName("university_name")
    private final String universityName;

    @SerializedName("file_url")
    private final String fileUrl;

        @SerializedName("created_at")
    private final String createdAt;

    @SerializedName("download_number")
    private final Integer downloadNumber;

    @SerializedName("writer")
    private final User writer;

    @SerializedName("doctor")
    private final User doctor;

    @SerializedName("field")
    private final Field field;

    @SerializedName("approved")
    private final ApprovedArticle approved;

    @SerializedName("banned")
    private final BannedArticle banned;

    @SerializedName("comments")
    private final ArrayList<Comment> comments;

    public Article(Integer id, String name,
                   String type, String universityName,
                   String fileUrl, String createdAt,
                   Integer downloadNumber,
                   User writer, User doctor, Field field,
                   ApprovedArticle approved, BannedArticle banned, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.universityName = universityName;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
        this.downloadNumber = downloadNumber;
        this.writer = writer;
        this.doctor = doctor;
        this.field = field;
        this.approved = approved;
        this.banned = banned;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    public Integer getDownloadNumber() {
        return downloadNumber;
    }

    public User getWriter() {
        return writer;
    }

    public User getDoctor() {
        return doctor;
    }

    public Field getField() {
        return field;
    }

    public ApprovedArticle getApproved() {
        return approved;
    }

    public BannedArticle getBanned() {
        return banned;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
