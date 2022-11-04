package edu.whu.homework7.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);
    final Map<String, Integer> callCount = Collections.synchronizedMap(new HashMap<>());
    final Map<String, Integer> exceptionCount = Collections.synchronizedMap(new HashMap<>());

    @Before("execution(* edu.whu.homework7.controller.*.*(..))")
    public void beforeController(JoinPoint jp) {
        String key = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        int count = callCount.getOrDefault(key, 0) + 1;
        String info = "进入" + key + "方法(第" + count + "次调用)";
        callCount.put(key, count);
        logger.info(info);
    }

    @AfterThrowing(pointcut = "execution(* edu.whu.homework7.controller.*.*(..))", throwing = "ex")
    public void afterControllerThrows(JoinPoint jp, Exception ex) {
        String key = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        int count = exceptionCount.getOrDefault(key, 0) + 1;
        String info = key + "方法第" + count + "次发生异常，异常为" + ex.getLocalizedMessage();
        exceptionCount.put(key, count);
        logger.error(info);
    }

    public Map<String, Integer> getCallCountList() {
        return callCount;
    }

    public Map<String, Integer> getExceptionCountList() {
        return exceptionCount;
    }
}
