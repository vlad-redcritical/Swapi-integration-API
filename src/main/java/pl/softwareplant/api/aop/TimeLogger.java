package pl.softwareplant.api.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeLogger {

    @Around("execution(* *(..)) && @annotation(pl.softwareplant.api.aop.ExecutionTimeLogger)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        log.info("Report generation info: className={}, methodName={}, timeMs={}, threadId={}",
                point.getSignature().getDeclaringTypeName(),
                ((MethodSignature) point.getSignature()).getMethod().getName(),
                System.currentTimeMillis() - start,
                Thread.currentThread().getId());
        return result;
    }
}