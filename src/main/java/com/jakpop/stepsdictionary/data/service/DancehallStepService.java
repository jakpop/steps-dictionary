package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import org.apache.commons.lang3.StringUtils;
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

    public List<DancehallStep> findByParams(String name, String creator, String period, String type) {
        List<DancehallStep> steps = repository.findAll();

        if (StringUtils.isNotBlank(name)) {
            steps = steps.stream().filter(step -> step.getName().equals(name)).collect(Collectors.toList());
        }
        if (StringUtils.isNotBlank(creator)) {
            steps = steps.stream().filter(step -> step.getCreator().equals(creator)).collect(Collectors.toList());
        }
        if (StringUtils.isNotBlank(period)) {
            steps = steps.stream().filter(step -> step.getPeriod().equals(period)).collect(Collectors.toList());
        }
        if (StringUtils.isNotBlank(type)) {
            steps = steps.stream().filter(step -> step.getType().equals(type)).collect(Collectors.toList());
        }

        return steps;
    }
}
