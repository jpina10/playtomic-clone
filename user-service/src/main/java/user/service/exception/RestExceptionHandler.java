package user.service.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import user.service.exception.model.AccessException;
import user.service.exception.model.ResourceAlreadyExistsException;
import user.service.exception.model.ResourceNotFoundException;
import user.service.exception.rest.RestErrorMessage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    private static final String SPACE = " ";
    private static final String DELIMITER = ", ";

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<RestErrorMessage> resourceNotFoundHandler(ResourceNotFoundException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> resourceAlreadyExistsHandler(ResourceAlreadyExistsException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(AccessException.class)//for specification
    private ResponseEntity<RestErrorMessage> illegalAccessHandler(AccessException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private ResponseEntity<RestErrorMessage> uniqueConstraintViolationHandler(SQLIntegrityConstraintViolationException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //for custom annotations
    private ResponseEntity<RestErrorMessage> invalidInputHandler(MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + SPACE + fieldError.getDefaultMessage())
                .collect(Collectors.joining(DELIMITER));

        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message);

        return new ResponseEntity<>(restErrorMessage, restErrorMessage.error());
    }

    private ResponseEntity<RestErrorMessage> handleException(Exception exception) {
        HttpStatus httpStatus = getHttpStatus(exception);

        RestErrorMessage restErrorMessage = new RestErrorMessage(httpStatus.value(), httpStatus, exception.getMessage());

        return new ResponseEntity<>(restErrorMessage, restErrorMessage.error());
    }

    private static HttpStatus getHttpStatus(Exception exception) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);

        return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
