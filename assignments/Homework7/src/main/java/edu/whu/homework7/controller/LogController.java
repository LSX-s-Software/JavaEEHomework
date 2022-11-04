package edu.whu.homework7.controller;

import edu.whu.homework7.aspect.LogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogAspect logAspect;

    @RequestMapping("/requestCount")
    public Map<String, Integer> getRequestCount() {
        return logAspect.getCallCountList();
    }

    @RequestMapping("/exceptionCount")
    public Map<String, Integer> getExceptionCount() {
        return logAspect.getExceptionCountList();
    }
}
