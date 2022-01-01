package com.rony.oracleemployee.exception;

public class InvalidPayloadException extends RuntimeException {
    public InvalidPayloadException(String message) {
        super(message);
    }
}
