package com.bigtree.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class OrdersExceptionHandler {
    
    /**
     * Exception handled when BadCredentials supplied
     */
    @ExceptionHandler(value ={BadCredentialsException.class})
    public ResponseEntity<String> badCredentials(BadCredentialsException e, WebRequest w){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


    /**
     * Exception handled when user passed invalid or expired JWT on the Authrization header
     */
    @ExceptionHandler(value ={InvalidTokenException.class})
    public ResponseEntity<Void> invalidJwt(InvalidTokenException e, WebRequest w){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}