package com.cl.aop;

import com.cl.domain.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
 * 异常aop
 * */
@Aspect
@Component
public class ExceptionAop {
    @Pointcut("execution(* com.cl.controller..*.*(..))")
    public void pointExpression() {

    }

    @Around("pointExpression()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof Exception) {
                throwable.printStackTrace();
                return ResponseResult.fail(throwable.getMessage());
            }
            throwable.printStackTrace();
        }
        return obj;
    }

}
