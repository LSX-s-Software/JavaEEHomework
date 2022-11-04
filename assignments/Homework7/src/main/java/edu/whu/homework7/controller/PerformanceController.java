package edu.whu.homework7.controller;

import edu.whu.homework7.aspect.StopwatchAspect;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stat/responseTime")
public class PerformanceController {
    @Autowired
    StopwatchAspect stopwatchAspect;

    @RequestMapping("/fastest")
    public Map<String, Long> getFastestResponseTime() {
        return stopwatchAspect.getFastestResponseTime();
    }

    @RequestMapping("/slowest")
    public Map<String, Long> getSlowestResponseTime() {
        return stopwatchAspect.getSlowestResponseTime();
    }

    @RequestMapping("/average")
    public Map<String, Double> getAverageResponseTime() {
        return stopwatchAspect.getAverageResponseTime();
    }
}
