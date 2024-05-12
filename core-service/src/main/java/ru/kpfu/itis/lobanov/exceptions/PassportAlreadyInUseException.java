package ru.kpfu.itis.lobanov.exceptions;

public class PassportAlreadyInUseException extends RuntimeException {
    public PassportAlreadyInUseException() {
    }

    public PassportAlreadyInUseException(String message) {
        super(message);
    }

    public PassportAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassportAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public PassportAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
