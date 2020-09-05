package cf.scenecho.dinner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    static final String DIR = "error/";
    public static final String UNEXPECTED = DIR + "unexpected";
    public static final String USERNAME_NOT_FOUND = DIR + "username-not-found";
    public static final String DINNER_EXCEPTION = DIR + "dinner-exception";

    @ExceptionHandler
    public String handleRuntimeException(HttpServletRequest request, RuntimeException exception) {
        log.info("RuntimeException: requested - {}, message - {}", request.getRequestURI(), exception.getMessage());
        return UNEXPECTED;
    }

    @ExceptionHandler
    public String handleUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException exception) {
        log.info("UsernameNotFoundException: requested - {}, message - {}", request.getRequestURI(), exception.getMessage());
        return USERNAME_NOT_FOUND;
    }

    @ExceptionHandler
    public String handleDinnerException(HttpServletRequest request, DinnerException exception) {
        log.info("DinnerException: requested - {}, exception - {}", request.getRequestURI(), exception.getMessage());
        return DINNER_EXCEPTION;
    }

}
