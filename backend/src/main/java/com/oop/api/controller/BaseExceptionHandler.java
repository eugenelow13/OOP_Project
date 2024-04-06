package com.oop.api.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.oop.api.util.ResponseHandler;

import jakarta.persistence.EntityNotFoundException;
import static com.oop.api.util.ExceptionMessageFormatter.getSimpleDataIntegrityExceptionMessage;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        e.printStackTrace();
        return ResponseHandler.generateResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception e) {

        String simpleExceptionMessage = getSimpleDataIntegrityExceptionMessage(e);

        return ResponseHandler.generateResponse(simpleExceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

}
