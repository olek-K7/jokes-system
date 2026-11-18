package app.dev.jokes_system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JokeTimingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(JokeTimingAspect.class);

    // Define which methods I want to intercept: Select the service methods that create, update or delete jokes.
    @Pointcut("""
            execution(* app.dev.jokes_system.service.JokeService.createJoke(..)) ||
            execution(* app.dev.jokes_system.service.JokeService.updateJokeRating(..)) ||
            execution(* app.dev.jokes_system.service.JokeService.deleteJokeById(..)) ||
            execution(* app.dev.jokes_system.service.JokeService.deleteJokesByCategory(..))
            """)
    public void jokeModificationMethods() {}

    // When logging happens: around the execution of the methods
    // Measure execution time and log whether the operation succeeds or fails.
    @Around("jokeModificationMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long startTime = System.nanoTime();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();
            long duration = (System.nanoTime() - startTime) / 1_000_000;
            logger.info("{} completed in {} ms", methodName, duration);

            return result;

        } catch (Throwable exception) {
            long duration = (System.nanoTime() - startTime) / 1_000_000;
            logger.warn("{} failed after {} ms: {}",
                    methodName, duration, exception.getMessage());

            throw exception;
        }
    }
}