package com.jakpop.stepsdictionary.data.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class DancehallStep extends AbstractEntity {
    private String name;
    private String creator;
    private String period;
    private String description;
    private String videoUrl;
}
