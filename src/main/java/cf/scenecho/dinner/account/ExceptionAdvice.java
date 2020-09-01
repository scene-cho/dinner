package cf.scenecho.dinner.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    public String handleRuntimeException(HttpServletRequest request, RuntimeException exception) {
        log.info("requested {}", request.getRequestURI());
        //TODO
//        log.error("bad request", e);
        return "";
    }
}
