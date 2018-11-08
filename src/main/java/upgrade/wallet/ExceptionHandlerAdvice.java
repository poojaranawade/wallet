package upgrade.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.toString());
    }
}