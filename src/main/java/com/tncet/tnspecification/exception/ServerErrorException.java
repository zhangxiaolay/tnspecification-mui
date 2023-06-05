package com.tncet.tnspecification.exception;

public class ServerErrorException extends RuntimeException{
    private static final long serialVersionUID = 6989299113740391578L;

    public ServerErrorException() {
        super();
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
