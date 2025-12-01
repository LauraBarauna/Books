package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import services.CommentService;

import java.util.Objects;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private Logger logger =
            Logger.getLogger(CommentService.class.getName());

    @Around("execution(* services..*(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method will execute");
        joinPoint.proceed();
        logger.info("Method executed");
    }

}
