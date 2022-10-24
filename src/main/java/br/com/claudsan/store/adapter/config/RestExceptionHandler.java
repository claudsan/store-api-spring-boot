package br.com.claudsan.store.adapter.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return handleExceptionInternal(ex, String.format("{\"error\":\"Record(s) not found on database\", \"cause\": \"%s\"}", ex.getLocalizedMessage()),
               headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> IntegrityViolationException(
            Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return handleExceptionInternal(ex, String.format("{\"error\":\"Some of the items sent does not exist in the database \", \"cause\": \"%s\"}", ex.getLocalizedMessage()),
                headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> runtimeError(
            Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return handleExceptionInternal(ex, String.format("{\"error\":\"Operation not performed, check the cause. Or contact the system admin.\", \"cause\": \"%s\"}", ex.getLocalizedMessage()),
                headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

}
