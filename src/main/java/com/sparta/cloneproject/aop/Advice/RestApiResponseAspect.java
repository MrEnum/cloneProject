package com.sparta.cloneproject.aop.Advice;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestApiResponseAspect {

    @Around("execution(* com.sparta.cloneproject.controller..*(..))")
    public Object addStatus(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed();

        } finally {
            long finish = System.currentTimeMillis();
            long timsMs = finish - start;
            System.out.println("END: " + joinPoint.toLongString() + " " + timsMs + "ms");
        }
    }
}
