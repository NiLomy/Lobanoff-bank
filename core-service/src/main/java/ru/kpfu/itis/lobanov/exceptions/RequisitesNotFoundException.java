package ru.kpfu.itis.lobanov.exceptions;

public class RequisitesNotFoundException extends RuntimeException {
    public RequisitesNotFoundException() {
    }

    public RequisitesNotFoundException(String message) {
        super(message);
    }

    public RequisitesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequisitesNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequisitesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
