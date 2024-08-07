package ru.lenazavupach.documentzxc.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lenazavupach.documentzxc.baseresponse.BaseResponseService;
import ru.lenazavupach.documentzxc.baseresponse.ResponseWrapper;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionApiHandler {
    private final BaseResponseService baseResponseService;

    @ExceptionHandler(Throwable.class)
    public ResponseWrapper<?> handleOtherException(Throwable t) {
        log.error("Got exception {}, message: {}", t.getClass(), t.getMessage());
        return baseResponseService.wrapErrorResponse(new CurrentException(ErrorType.COMMON_ERROR, t));
    }

    @ExceptionHandler(CurrentException.class)
    public ResponseWrapper<?> handleCurrentException(CurrentException ex) {
        if (ex.getType() == ErrorType.NOT_FOUND) {
            log.error("Got not found exception {}, message: {}", ex.getClass(), ex.getMessage());
            return baseResponseService.wrapErrorResponse(ex);
        }
        return baseResponseService.wrapErrorResponse(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseWrapper<?> handleValidationException(Exception e) {
        log.error("Got validation exception {}, message: {}", e.getClass(), e.getMessage());
        return baseResponseService.wrapErrorResponse(new CurrentException(ErrorType.CLIENT_ERROR, e));
    }
}
