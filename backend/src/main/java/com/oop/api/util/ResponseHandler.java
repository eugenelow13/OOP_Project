package com.oop.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, Object data, HttpStatusCode status) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (message != null)
            map.put("message", message);

        map.put("data", data);
        return new ResponseEntity<Object>(map, status);
    }

}
