package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspects {

    private Logger logger
            = Logger.getLogger(LoggingAspects.class.getName());

    @Around("@annotation(ToLog)")
    public Object log (ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Login Aspect: Calling the intercepted method");

        Object returnedValue = joinPoint.proceed();

        logger.info("Login Aspect: Method executed and returned " + returnedValue);
        return returnedValue;
    }
}
