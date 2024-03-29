package com.alihmzyv.notebookrestapi.exception.handler;

import com.alihmzyv.notebookrestapi.exception.dublicate.DuplicateNotAllowedException;
import com.alihmzyv.notebookrestapi.exception.model.CustomErrorResponse;
import com.alihmzyv.notebookrestapi.exception.notfound.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = ex.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
        return ResponseEntity
                .badRequest()
                .body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), messages));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNotFound(NotFoundException exc) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(exc.getMessage())));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlePSQL(SQLException exc) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(exc.getMessage())));
    }

    @ExceptionHandler({DuplicateNotAllowedException.class, PropertyReferenceException.class})
    public ResponseEntity<Object> handlePropertyReference(Exception exc) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(exc.getMessage())));
    }
}
