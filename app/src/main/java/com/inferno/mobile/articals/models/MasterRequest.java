package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MasterRequest {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("type")
    private final String type;
    @SerializedName("university_name")
    private final String universityName;
    @SerializedName("file_url")
    private final String fileUrl;
    @SerializedName("created_at")
    private final Date createAt;
    @SerializedName("writer")
    private final User master;

    public MasterRequest(int id, String name, String type,
                         String universityName,
                         String fileUrl, Date createAt,
                         User master) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.universityName = universityName;
        this.fileUrl = fileUrl;
        this.createAt = createAt;
        this.master = master;
    }

    public int getId() {
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

    public Date getCreateAt() {
        return createAt;
    }

    public User getMaster() {
        return master;
    }
}
