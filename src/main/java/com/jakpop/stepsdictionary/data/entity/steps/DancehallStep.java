package com.jakpop.stepsdictionary.data.entity.steps;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakpop.stepsdictionary.data.entity.users.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;


@Data
@Document(collection = "dancehallsteps")
public class DancehallStep {
    @Id
    protected String id;
    protected String name;
    protected String videoUrl;
    private String creator;
    private String period;
    private String type;

    @JsonIgnore
    protected Instant createDate;
    @JsonIgnore
    protected Instant updateDate;
    @JsonIgnore
    protected User addedBy;
    @JsonIgnore
    protected User updatedBy;

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void setDate() {
        if (this.createDate == null) {
            this.createDate = Instant.now();
        }
        this.updateDate = Instant.now();
    }

    public void setAddedBy(User user) {
        if (this.addedBy == null) {
            this.addedBy = user;
        }
        this.updatedBy = user;
    }

    public void init(User user) {
        this.setId();
        this.setDate();
        this.setAddedBy(user);
    }
}
