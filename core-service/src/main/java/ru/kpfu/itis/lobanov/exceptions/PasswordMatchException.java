package ru.kpfu.itis.lobanov.exceptions;

public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException() {
    }

    public PasswordMatchException(String message) {
        super(message);
    }

    public PasswordMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
