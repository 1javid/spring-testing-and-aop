package com.example.restful.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class UserServiceLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceLogAspect.class);
    @Pointcut("execution(* com.example.restful.service.UserService.*(..))")
    private void methodPointCut() {
    }

    @Before("methodPointCut()")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        LOGGER.info("BEFORE - {} method executed with parameters: {}", methodName, Arrays.toString(args));
    }

    @SneakyThrows
    @Around("methodPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        StopWatch watch = new StopWatch();

        watch.start();
        Object proceed = joinPoint.proceed();
        watch.stop();

        LOGGER.info("AFTER - execution time: {} executed in {} ms", joinPoint.getSignature().getName(), watch.getTotalTimeMillis());
        return proceed;
    }

    @AfterReturning(value = "methodPointCut()", returning = "result")
    public void afterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("AFTER - {} method executed with return value: {}", methodName, result);
    }
}