package com.jakpop.stepsdictionary.data.entity.steps;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "dancehallsteps")
public class DancehallStep extends BaseStep {
    private String creator;
    private String period;
    private String type;
}
