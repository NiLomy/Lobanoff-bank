package ru.kpfu.itis.lobanov.exceptions;

public class TransactionMethodNotFoundException extends RuntimeException {
    public TransactionMethodNotFoundException() {
    }

    public TransactionMethodNotFoundException(String message) {
        super(message);
    }

    public TransactionMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public TransactionMethodNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
