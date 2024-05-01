package ru.kpfu.itis.lobanov.exceptions;

public class InvalidAmountOfTransactionException extends RuntimeException {
    public InvalidAmountOfTransactionException() {
    }

    public InvalidAmountOfTransactionException(String message) {
        super(message);
    }

    public InvalidAmountOfTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAmountOfTransactionException(Throwable cause) {
        super(cause);
    }

    public InvalidAmountOfTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
