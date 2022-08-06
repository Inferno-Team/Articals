package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;
import com.inferno.mobile.articals.utils.RequestStatus;


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
    @SerializedName("create_at")
    private final String createAt;
    @SerializedName("writer")
    private final User master;
    @SerializedName("status")
    private  RequestStatus status;

    public MasterRequest(int id, String name, String type,
                         String universityName,
                         String fileUrl, String createAt,
                         User master, RequestStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.universityName = universityName;
        this.fileUrl = fileUrl;
        this.createAt = createAt;
        this.master = master;
        this.status = status;
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

    public String getCreateAt() {
        return createAt;
    }

    public User getMaster() {
        return master;
    }

    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status){
        this.status = status;
    }
}
