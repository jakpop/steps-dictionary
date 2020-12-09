package com.jakpop.stepsdictionary.data.entity.enums;

public enum Period {
    OLD_SCHOOL("old school", "80s"),
    MIDDLE_SCHOOL("middle school", "90s"),
    NEW_SCHOOL("new school", "2000s"),
    OTHER("other", "other");

    private String name;
    private String dates;

    Period(String name, String dates) {
        this.name = name;
        this.dates = dates;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }
}
