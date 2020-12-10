package com.jakpop.stepsdictionary.data.entity.steps;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@Document(collection = "dancehallsteps")
public class DancehallStep {
    @Id
    private String id;
    private String name;
    private String creator;
    private String period;
    private String type;
    private String description;
    private String videoUrl;

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}