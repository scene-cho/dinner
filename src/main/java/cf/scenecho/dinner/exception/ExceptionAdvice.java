package cf.scenecho.dinner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    public static final String SERVER_ERROR_PAGE = "5xx";
    public static final String BAD_REQUEST_PAGE = "404";

    @ExceptionHandler
    public String handleRuntimeException(HttpServletRequest request, RuntimeException exception) {
        log.info("requested {}, exception {}", request.getRequestURI(), exception.getMessage());
        return SERVER_ERROR_PAGE;
    }

    @ExceptionHandler
    public String handleDinnerException(HttpServletRequest request, DinnerException exception) {
        log.info("requested {}, exception {}", request.getRequestURI(), exception.getMessage());
        return SERVER_ERROR_PAGE;
    }

    @ExceptionHandler
    public String handleAccountException(HttpServletRequest request, AccountException exception) {
        log.info("requested {}, exception {}", request.getRequestURI(), exception.getMessage());
        return SERVER_ERROR_PAGE;
    }

    @ExceptionHandler
    public String handleUserNotFoundException(HttpServletRequest request, UserNotFoundException exception) {
        log.info("requested {}, exception {}", request.getRequestURI(), exception.getMessage());
        return BAD_REQUEST_PAGE;
    }
}
