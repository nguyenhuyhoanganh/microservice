package com.example.base.exception;

import com.example.base.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
        ResponseDTO<?> responseBody = ResponseDTO.builder().message(exception.getMessage()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
