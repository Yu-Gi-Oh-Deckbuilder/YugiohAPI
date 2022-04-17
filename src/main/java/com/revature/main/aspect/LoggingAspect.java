package com.revature.main.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    public static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(value = "execution(* com.revature.main.*.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        logger.info("Starting request on " + joinPoint.getSignature());
    }

    @After(value = "execution(* com.revature.main.*.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        logger.info("Request Completed on " + joinPoint.getSignature());
    }

}
