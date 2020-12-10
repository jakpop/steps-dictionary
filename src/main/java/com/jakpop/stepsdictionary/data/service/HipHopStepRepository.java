package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.steps.HipHopStep;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HipHopStepRepository extends MongoRepository<HipHopStep, String> {

}