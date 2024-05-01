package ru.kpfu.itis.lobanov.exceptions;

public class TransactionTypeNotFoundException extends RuntimeException {
    public TransactionTypeNotFoundException() {
    }

    public TransactionTypeNotFoundException(String message) {
        super(message);
    }

    public TransactionTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public TransactionTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
