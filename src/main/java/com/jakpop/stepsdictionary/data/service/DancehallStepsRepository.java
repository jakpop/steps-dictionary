package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DancehallStepsRepository extends MongoRepository<DancehallStep, String> {
}