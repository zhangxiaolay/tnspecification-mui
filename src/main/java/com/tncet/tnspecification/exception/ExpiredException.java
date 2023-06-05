package com.tncet.tnspecification.exception;

public class ExpiredException extends RuntimeException{
    private static final long serialVersionUID = 6989299113740391577L;

    public ExpiredException() {
    }

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
