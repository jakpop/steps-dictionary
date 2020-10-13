package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DancehallStepService extends CrudService<DancehallStep, Integer> {

    private final DancehallStepRepository repository;

    public DancehallStepService(@Autowired DancehallStepRepository repository) {
        this.repository = repository;
    }

    @Override
    protected DancehallStepRepository getRepository() {
        return repository;
    }

    public List<DancehallStep> findByParams(String name, String creator, String period) {
        List<DancehallStep> steps = repository.findAll();

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
