package ru.kpfu.itis.lobanov.exceptions;

public class PhoneAlreadyInUseException extends RuntimeException {
    public PhoneAlreadyInUseException() {
    }

    public PhoneAlreadyInUseException(String message) {
        super(message);
    }

    public PhoneAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public PhoneAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
