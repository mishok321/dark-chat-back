package com.misha.darkchatback.controller;

import com.misha.darkchatback.exception.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MafiaGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String TIMESTAMP_FIELD_NAME = "timestamp";
    public static final String STATUS_FIELD_NAME = "status";
    public static final String ERRORS_FIELD_NAME = "errors";

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(
                createBody(List.of(ex.getMessage()), HttpStatus.UNAUTHORIZED)
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(
                createBody(List.of("Login is already taken, please try another one"),
                        HttpStatus.CONFLICT)
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(createBody(
                errors, HttpStatus.UNAUTHORIZED
        ), headers, status);
    }

    public Map<String, Object> createBody(List<String> errors, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP_FIELD_NAME, LocalDateTime.now());
        body.put(STATUS_FIELD_NAME, status.value());
        body.put(ERRORS_FIELD_NAME, errors);
        return body;
    }
}
