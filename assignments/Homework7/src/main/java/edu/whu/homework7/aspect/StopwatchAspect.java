package edu.whu.homework7.aspect;

import kotlin.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class StopwatchAspect {

    final List<Pair<String, Long>> responseTimeList = Collections.synchronizedList(new ArrayList<>());

    @Around("execution(* edu.whu.homework7.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String key = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        responseTimeList.add(new Pair<>(key, executionTime));
        return proceed;
    }

    public void clearRecord() {
        responseTimeList.clear();
    }

    public Map<String, Double> getAverageResponseTime() {
        Map<String, Long> responseTimeMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        for (Pair<String, Long> pair : responseTimeList) {
            String key = pair.getFirst();
            Long value = pair.getSecond();
            responseTimeMap.put(key, responseTimeMap.getOrDefault(key, 0L) + value);
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        }
        Map<String, Double> averageResponseTimeMap = new HashMap<>();
        for (String key : responseTimeMap.keySet()) {
            averageResponseTimeMap.put(key, Double.valueOf(responseTimeMap.get(key)) / countMap.get(key));
        }
        return averageResponseTimeMap;
    }

    public Map<String, Long> getSlowestResponseTime() {
        Map<String, Long> responseTimeMap = new HashMap<>();
        for (Pair<String, Long> pair : responseTimeList) {
            String key = pair.getFirst();
            Long value = pair.getSecond();
            if (responseTimeMap.getOrDefault(key, 0L) < value) {
                responseTimeMap.put(key, value);
            }
        }
        return responseTimeMap;
    }

    public Map<String, Long> getFastestResponseTime() {
        Map<String, Long> responseTimeMap = new HashMap<>();
        for (Pair<String, Long> pair : responseTimeList) {
            String key = pair.getFirst();
            Long value = pair.getSecond();
            if (responseTimeMap.getOrDefault(key, Long.MAX_VALUE) > value) {
                responseTimeMap.put(key, value);
            }
        }
        return responseTimeMap;
    }
}
