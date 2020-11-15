package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.HipHopStep;

import lombok.RequiredArgsConstructor;
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
