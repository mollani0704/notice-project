package com.notice.project.handler.aop;

import java.sql.Time;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimeAop {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeAop.class);
	
	@Pointcut("execution(* com.notice.project.web.controller..*.*(..))")
	private void pointCut() {}
	
	@Pointcut("@annotation(com.notice.project.handler.aop.annotation.Timer)")
	private void enableTimer() {}
	
	@Around("pointCut() && enableTimer()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = joinPoint.proceed();
		
		for(Object arg : joinPoint.getArgs()) {
			LOGGER.info("arg: {}", arg);
		}
		
		stopWatch.stop();
		
		LOGGER.info(">>>>>>>>>>>>> {}({}) 메소드 실행 시간 : {}ms",
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		
		return result;
	}
	 
}
