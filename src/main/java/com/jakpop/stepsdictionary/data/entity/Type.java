package com.jakpop.stepsdictionary.data.entity;

public enum Type {
    SMOOTH("smooth"),
    BADMAN("badman"),
    FEMALE("female"),
    OTHER("other");

    private String name;


    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
