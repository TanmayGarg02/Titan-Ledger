package com.titan.ledger.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponse response = new ErrorResponse(Instant.now(), "INSUFFICIENT_BALANCE", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransaction(InvalidTransactionException ex) {
        ErrorResponse response = new ErrorResponse(Instant.now(), "INVALID_TRANSACTION", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response =
                new ErrorResponse(
                        Instant.now(),
                        "INTERNAL_ERROR",
                        "Something went wrong"
                );
        return ResponseEntity.internalServerError().body(response);

    }
}
