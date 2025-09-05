package com.ual.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
 
import lombok.extern.log4j.Log4j2;
 
@Component
@Aspect
@Log4j2
public class LoggingAspect {
 
	@AfterThrowing(pointcut = "execution(* com.ual.*.*.*(..))", throwing = "exception")
	public void logAfterThrowingAdviceDetails(JoinPoint joinPoint, Exception exception) {
		log.info("In After throwing Advice, Joinpoint signature :{}", joinPoint.getSignature());
		log.error(exception.getMessage(), exception);
 
	}
}
