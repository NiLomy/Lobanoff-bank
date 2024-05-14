package ru.kpfu.itis.lobanov.notificationservice.configs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.lobanov.notificationservice.aspects.LoggerAspect;

@Aspect
@Slf4j
@Configuration
public class LoggerConfig {
    @Bean
    public LoggerAspect aspect() {
        return new LoggerAspect();
    }

    @Around(value = "ru.kpfu.itis.lobanov.notificationservice.aspects.LoggerAspect.serviceMethod()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] argsOfMethod = joinPoint.getArgs();

        log.info("Call method: '{}' with args: {}.", methodName, argsOfMethod);

        Object result;
        long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        long executionTime = System.currentTimeMillis() - start;
        log.info("Method: '{}' was completed in '{}' ms, returns: {}.", methodName, executionTime, result);
        return result;
    }

    @AfterThrowing(value = "ru.kpfu.itis.lobanov.notificationservice.aspects.LoggerAspect.serviceMethod()")
    public void logThrowingException(JoinPoint joinPoint) {
        log.error("Exception at: {}.", joinPoint.getSignature().getName());
    }
}
