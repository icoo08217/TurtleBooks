package com.turtle.turtlebooks.app.member.exception;

public class NoMatchAuthLevelException extends RuntimeException{

    public NoMatchAuthLevelException() {
    }

    public NoMatchAuthLevelException(String message) {
        super(message);
    }

    public NoMatchAuthLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchAuthLevelException(Throwable cause) {
        super(cause);
    }

    public NoMatchAuthLevelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
