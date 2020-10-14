package com.jakpop.stepsdictionary.data.entity.enums;

public enum Period {
    OLD_SCHOOL("old school"),
    MIDDLE_SCHOOL("middle school"),
    NEW_SCHOOL("new school"),
    OTHER("other");

    private String name;

    Period(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
