package aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspects {

    private Logger logger
            = Logger.getLogger(LoggingAspects.class.getName());

    @AfterReturning(value = "@annotation(aspects.ToLog)",
        returning = "result")
    public void log (Object result) throws Throwable {
        logger.info("Finish executing method, the return is " + result);
    }
}
