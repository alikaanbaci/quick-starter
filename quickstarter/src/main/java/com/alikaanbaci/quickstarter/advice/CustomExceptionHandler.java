package com.alikaanbaci.quickstarter.advice;

import com.alikaanbaci.quickstarter.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final Map<String, Object> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(fieldError -> Objects.nonNull(fieldError.getDefaultMessage()))
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity
                .badRequest()
                .body(Response.builder()
                        .errors(errors)
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<?> exception(Exception ex, WebRequest webRequest) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .internalServerError()
                .body(Response.builder()
                        .errors("An unexpected error happened.")
                        .build());
    }
}
