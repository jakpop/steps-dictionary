package com.jakpop.stepsdictionary.data.entity.steps;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "hiphopsteps")
public class HipHopStep extends BaseStep {
    private String period;
}
