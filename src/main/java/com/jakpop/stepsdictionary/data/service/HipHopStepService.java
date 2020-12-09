package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.HipHopStep;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HipHopStepService extends CrudService<HipHopStep, String> {

    private final HipHopStepRepository repository;

    @Override
    protected HipHopStepRepository getRepository() {
        return repository;
    }

    public List<HipHopStep> findByParams(String name, String period) {
        List<HipHopStep> steps = repository.findAll();

        if (StringUtils.isNotBlank(name)) {
            steps = steps.stream().filter(step -> step.getName().equals(name)).collect(Collectors.toList());
        }
        if (StringUtils.isNotBlank(period)) {
            steps = steps.stream().filter(step -> step.getPeriod().equals(period)).collect(Collectors.toList());
        }

        return steps;
    }

}
