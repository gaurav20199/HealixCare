package com.project.patientmanagementservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error)->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String,String>> handleInvalidEmailException(InvalidEmailException ex){
        log.warn("Email id provided is invalid",ex.getMessage());
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message","Invalid email id provided");
        return ResponseEntity.badRequest().body(errorMap);
    }
}
