package com.notice.project.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.notice.project.handler.CustomValidationApiException;

@Aspect
@Component
public class ValidCheckAop {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidCheckAop.class);
	
	@Pointcut("@annotation(com.notice.project.handler.aop.annotation.ValidCheck)")
	public void enableValid() {};
	
	@Before("enableValid()")
	public void validCheck(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		Map<String, String> errorMap = new HashMap<String, String>();
		LOGGER.info(">>>>>>>>>>> 유효성 검사 중.....");
		
		for(Object arg : args) {
			if(arg.getClass() == BeanPropertyBindingResult.class) {
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					bindingResult.getFieldErrors().forEach(error -> {
						errorMap.put(error.getField(), error.getDefaultMessage());
					});
				}
			}
		}
		throw new CustomValidationApiException("유효성 검사", errorMap);
	}
	
	@AfterReturning(value = "enableValid()", returning = "returnObj")
	public void afterReturn(JoinPoint joinPoint, Object returnObj) {
		LOGGER.info("유효성 검사 완료 : {} ", returnObj);
	}
	
}
