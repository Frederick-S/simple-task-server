package dekiru.simpletask.controller;

import dekiru.simpletask.dto.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * The global exception handler for controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle method argument not valid exception.
     *
     * @param e {@link org.springframework.web.bind.MethodArgumentNotValidException}
     * @return The error entity {@link ResponseError}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message = fieldErrors.stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid argument");
        ResponseError error = new ResponseError(message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ResponseError("HTTP message not readable"),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle MissingServletRequestParameterException.
     *
     * @param e {@link org.springframework.web.bind.MissingServletRequestParameterException}
     * @return The error entity {@link ResponseError}
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseError> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        String message = String.format("Parameter %s is missing", e.getParameterName());
        ResponseError error = new ResponseError(message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException.
     *
     * @param e {@link IllegalArgumentException}
     * @return The error entity {@link ResponseError}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgumentException(
            IllegalArgumentException e) {
        ResponseError error = new ResponseError(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle other exceptions.
     *
     * @param e {@link java.lang.Throwable}
     * @return The error entity {@link ResponseError}
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseError> handleRuntimeException(Throwable e) {
        logger.error("Controller error", e);

        return new ResponseEntity<>(new ResponseError("Interval server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
