package ru.kpfu.itis.lobanov.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAspect {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethod() {
    }
}
