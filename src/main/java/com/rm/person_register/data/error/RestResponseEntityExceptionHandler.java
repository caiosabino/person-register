package com.rm.person_register.data.error;

import com.rm.person_register.data.dto.base.Response;
import com.rm.person_register.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    private static final String TIMESTAMP = "timestamp";
    private static final String HTTP_CODE = "httpCode";
    private static final String HTTP_STATUS = "httpStatus";
    private static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<?> handleUnexpectedErrors(final Exception ex, final WebRequest request) {
        final String responseBody = ex.getMessage();

        log.error(Arrays.toString(ex.getStackTrace()));
        log.error(responseBody, ex);

        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleBadRequest(final Exception ex) {
        log.error(ex.getMessage());
        log.error(Arrays.toString(ex.getStackTrace()));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<?> handleNotFound(final Exception ex) {
        log.error(ex.getMessage());
        log.error(Arrays.toString(ex.getStackTrace()));
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    public static ResponseEntity<Response<Map<String, Object>>> buildErrorResponse(HttpStatus status, String message) {
        final Response<Map<String, Object>> response = Response.<Map<String, Object>>builder().build();
        response.setData(buildErrorInfo(status, message));
        response.setMessage(Arrays.asList(message));

        return new ResponseEntity<>(response, status);
    }

    public static Map<String, Object> buildErrorInfo(HttpStatus httpStatus, String message) {
        Map<String, Object> errorInfo = new LinkedHashMap<>();
        errorInfo.put(TIMESTAMP, new Date());
        errorInfo.put(HTTP_CODE, httpStatus.value());
        errorInfo.put(HTTP_STATUS, httpStatus.getReasonPhrase());
        errorInfo.put(ERROR_MESSAGE, message);
        return errorInfo;
    }
}
