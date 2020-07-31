package com.tms.api.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, ex);
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotUniqueEntryException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler
//    public ResponseEntity<?> handleException(HttpClientErrorException.BadRequest ex){
//        System.out.println("Catching bad request");
//        return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
//    }
}





