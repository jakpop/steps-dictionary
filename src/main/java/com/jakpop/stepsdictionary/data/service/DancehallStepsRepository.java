package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.steps.DancehallStep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancehallStepsRepository extends MongoRepository<DancehallStep, String> {
}