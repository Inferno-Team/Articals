package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private final int id;
    @SerializedName("first_name")
    private final String firstName;
    @SerializedName("last_name")
    private final String lastName;
    @SerializedName("email")
    private final String email;
    @SerializedName("type")
    private final UserType type;
    @SerializedName("field")
    private final Field field;
    @SerializedName("approved")
    private final ApprovalType approvalType;
    @SerializedName("created_at")
    private final String createdAt;

    public User(int id, String firstName, String lastName,
                String email, UserType type,
                Field field, ApprovalType approvalType, String createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.field = field;
        this.approvalType = approvalType;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
