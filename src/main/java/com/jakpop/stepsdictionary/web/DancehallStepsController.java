package com.jakpop.stepsdictionary.web;

import com.jakpop.stepsdictionary.data.entity.DancehallStep;
import com.jakpop.stepsdictionary.data.service.DancehallStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dancehall")
@RequiredArgsConstructor
public class DancehallStepsController {
    private final DancehallStepService dancehallStepService;

    @GetMapping
    public List<DancehallStep> getDancehallSteps(@RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "creator", required = false) String creator,
                                                 @RequestParam(value = "period", required = false) String period) {
        return dancehallStepService.findByParams(name, creator, period);
    }

    @GetMapping("/{id}")
    public Optional<DancehallStep> getDancehallStepsById(@PathVariable Integer id) {
        return dancehallStepService.get(id);
    }

    @PostMapping
    public DancehallStep createDacehallStep(@RequestBody DancehallStep step) {
        step.setId();
        dancehallStepService.update(step);
        return step;
    }

    @PutMapping("/{id}")
    public DancehallStep updateDancehallStep(@PathVariable Integer id, @RequestBody DancehallStep step) {
        step.setId(id);
        dancehallStepService.update(step);
        return step;
    }

    @DeleteMapping("/{id}")
    public void deleteDancehallStep(@PathVariable Integer id) {
        dancehallStepService.delete(id);
    }
}
