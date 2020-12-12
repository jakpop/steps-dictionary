package com.jakpop.stepsdictionary.data.entity.steps;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jakpop.stepsdictionary.data.entity.users.User;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Data
public class BaseStep {
    @Id
    protected String id;
    protected String name;
    protected String videoUrl;
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
}
