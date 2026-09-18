package by.filage.expensetrackerpractice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* by.filage.expensetrackerpractice.service..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        String method = joinPoint.getSignature().toShortString();
        String parameters = Arrays.toString(joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();

            long executionTime = (System.nanoTime() - startTime) / 1000000;

            log.info(
                    "method={}, parameters={}, returnValue={}, executionTimeMs={}",
                    method,
                    parameters,
                    result,
                    executionTime
            );

            return result;
        } catch (Throwable exception) {
            long executionTime = (System.nanoTime() - startTime) / 1000000;

            log.error(
                    "method={}, parameters={}, exception={}, executionTimeMs={}",
                    method,
                    parameters,
                    exception.toString(),
                    executionTime,
                    exception
            );

            throw exception;
        }
    }
}
