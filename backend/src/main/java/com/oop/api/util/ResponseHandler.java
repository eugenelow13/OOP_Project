package com.oop.api.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * The ResponseHandler class provides utility methods for generating HTTP responses.
 */
public class ResponseHandler {
    
    /**
     * Generates an HTTP response with a message, data, and status code.
     *
     * @param message The message to include in the response.
     * @param data The data to include in the response.
     * @param status The HTTP status code of the response.
     * @return A ResponseEntity containing the response data and status code.
     */
    public static ResponseEntity<Object> generateResponse(String message, Object data, HttpStatusCode status) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (message != null)
            map.put("message", message);

        map.put("data", data);
        return new ResponseEntity<Object>(map, status);
    }

    /**
     * Generates an HTTP response with data and the default status code (200 OK).
     *
     * @param data The data to include in the response.
     * @return A ResponseEntity containing the response data and status code.
     */
    public static ResponseEntity<Object> generateResponse(Object data) {
        return generateResponse(null, data, HttpStatus.OK);
    }

    /**
     * Generates an HTTP response with a message, data, and the default status code (200 OK).
     *
     * @param message The message to include in the response.
     * @param data The data to include in the response.
     * @return A ResponseEntity containing the response data and status code.
     */
    public static ResponseEntity<Object> generateResponse(String message, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (message != null)
            map.put("message", message);

        map.put("data", data);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}