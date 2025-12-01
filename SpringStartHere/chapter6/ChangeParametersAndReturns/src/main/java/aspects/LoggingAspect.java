package aspects;

import model.Comment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import services.CommentService;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private Logger logger =
            Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* services..*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Comment comment = new Comment();
        comment.setText("Some other text!");
        Object [] newArgs = {comment};

        logger.info("Method " + methodName + " executed with args: " + Arrays.asList(args) + " will execute");
        Object returnedByMethod = joinPoint.proceed(newArgs);
        logger.info("Method executed and returned " + returnedByMethod);
        return "FAILED";
    }

}
