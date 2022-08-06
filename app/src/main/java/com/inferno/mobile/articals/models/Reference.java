package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

public class Reference {
    @SerializedName("name")
    private final String name;
    @SerializedName("id")
    private final int id;

    public Reference(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
