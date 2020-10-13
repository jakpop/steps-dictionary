package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DancehallStepRepository extends JpaRepository<DancehallStep, Integer> {

}