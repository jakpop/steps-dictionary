package com.jakpop.stepsdictionary.web;

import com.jakpop.stepsdictionary.data.entity.steps.DancehallStep;
import com.jakpop.stepsdictionary.data.service.DancehallStepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dancehall")
@RequiredArgsConstructor
public class DancehallStepsController {
    private final DancehallStepsService dancehallStepService;

    @GetMapping
    public List<DancehallStep> getDancehallSteps(@RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "creator", required = false) String creator,
                                                 @RequestParam(value = "period", required = false) String period,
                                                 @RequestParam(value = "type", required = false) String type) {
        return dancehallStepService.findByParams(name, creator, period, type);
    }

    @GetMapping("/{id}")
    public Optional<DancehallStep> getDancehallStepsById(@PathVariable String id) {
        return dancehallStepService.get(id);
    }

    @PostMapping
    public DancehallStep createDancehallStep(@RequestBody DancehallStep step) {
        step.setId();
        dancehallStepService.update(step);
        return step;
    }

    @PutMapping("/{id}")
    public DancehallStep updateDancehallStep(@PathVariable String id, @RequestBody DancehallStep step) {
        step.setId(id);
        dancehallStepService.update(step);
        return step;
    }

    @DeleteMapping("/{id}")
    public void deleteDancehallStep(@PathVariable String id) {
        dancehallStepService.delete(id);
    }
}
