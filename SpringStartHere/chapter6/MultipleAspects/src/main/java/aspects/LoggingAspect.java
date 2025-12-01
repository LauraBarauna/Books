package aspects;

import model.Comment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import services.CommentService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private Logger logger =
            Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(annotation.ToLog)")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println();

        String methodName = joinPoint.getSignature().getName();

        logger.info("Method " + methodName +  " will execute");
        Object returnedByMethod = joinPoint.proceed();
        logger.info("Method executed and returned " + returnedByMethod);
    }

}
