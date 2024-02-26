package com.oop.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.oop.api.util.ResponseHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), null, HttpStatus.NOT_FOUND);
    }

}
