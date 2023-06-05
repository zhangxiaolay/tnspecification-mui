package com.tncet.tnspecification.exception;

public class ParameterMissingException extends RuntimeException{
    public ParameterMissingException() {
    }

    public ParameterMissingException(String message) {
        super(message);
    }
}
