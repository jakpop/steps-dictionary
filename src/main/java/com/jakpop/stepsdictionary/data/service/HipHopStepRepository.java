package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.HipHopStep;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HipHopStepRepository extends JpaRepository<HipHopStep, Integer> {

}