package com.inferno.mobile.articals.models;

public class Field {
    private final int id;
    private final String name;

    public Field(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
