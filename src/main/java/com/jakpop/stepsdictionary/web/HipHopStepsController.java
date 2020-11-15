package com.jakpop.stepsdictionary.web;

import com.jakpop.stepsdictionary.data.entity.HipHopStep;
import com.jakpop.stepsdictionary.data.service.HipHopStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hiphop")
@RequiredArgsConstructor
public class HipHopStepsController {
    private final HipHopStepService hipHopStepService;

    @GetMapping
    public List<HipHopStep> getHipHopSteps(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "creator", required = false) String creator,
                                              @RequestParam(value = "period", required = false) String period) {
        return hipHopStepService.findByParams(name, creator, period);
    }

    @GetMapping("/{id}")
    public Optional<HipHopStep> getHipHopStepsById(@PathVariable String id) {
        return hipHopStepService.get(id);
    }

    @PostMapping
    public HipHopStep createDacehallStep(@RequestBody HipHopStep step) {
        step.setId();
        hipHopStepService.update(step);
        return step;
    }

    @PutMapping("/{id}")
    public HipHopStep updateHipHopStep(@PathVariable String id, @RequestBody HipHopStep step) {
        step.setId(id);
        hipHopStepService.update(step);
        return step;
    }

    @DeleteMapping("/{id}")
    public void deleteHipHopStep(@PathVariable String id) {
        hipHopStepService.delete(id);
    }
}
