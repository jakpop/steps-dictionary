package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}