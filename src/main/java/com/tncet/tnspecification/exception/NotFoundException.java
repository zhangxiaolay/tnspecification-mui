package com.tncet.tnspecification.exception;

public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = 6989299113740391579L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
