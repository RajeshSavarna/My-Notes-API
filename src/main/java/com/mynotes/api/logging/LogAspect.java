package com.mynotes.api.logging;


import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	
	@Around("@annotation(com.mynotes.api.logging.LogAround) && execution(* * (..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
        	log.info("--------> Entry in method : "+joinPoint.getSignature().toShortString()+ " ->"+Instant.now().getEpochSecond());
            result = joinPoint.proceed();         
            log.info("<-------- Exit from method : "+joinPoint.getSignature().toShortString()+ " ->"+Instant.now().getEpochSecond());
        } catch (Exception e) {
        	log.error("<--------> Exception in method : " + joinPoint.getSignature().toShortString() + "->"+Instant.now().getEpochSecond(), e);
            throw e;
        }
        return result;
    }
}