package com.titan.ledger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final Instant timestamp;
    private final String error;
    private final String message;
}
