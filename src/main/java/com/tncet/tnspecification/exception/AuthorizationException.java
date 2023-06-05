package com.tncet.tnspecification.exception;

public class AuthorizationException extends RuntimeException{
    private static final long serialVersionUID = 6989299113740391576L;

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
