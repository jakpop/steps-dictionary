package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import com.jakpop.stepsdictionary.data.entity.HipHopStep;
import com.jakpop.stepsdictionary.data.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HipHopStepService extends CrudService<HipHopStep, Integer> {

    private HipHopStepRepository repository;

    public HipHopStepService(@Autowired HipHopStepRepository repository) {
        this.repository = repository;
    }

    @Override
    protected HipHopStepRepository getRepository() {
        return repository;
    }

    public List<HipHopStep> findByParams(String name, String creator, String period) {
        List<HipHopStep> steps = repository.findAll();

        if (name != null) {
            steps = steps.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        }
        if (creator != null) {
            steps = steps.stream().filter(user -> user.getCreator().equals(creator)).collect(Collectors.toList());
        }
        if (period != null) {
            steps = steps.stream().filter(user -> user.getPeriod().equals(period)).collect(Collectors.toList());
        }

        return steps;
    }

}
