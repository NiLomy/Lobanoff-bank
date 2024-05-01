package ru.kpfu.itis.lobanov.exceptions;

public class InvalidBinException extends RuntimeException {
    public InvalidBinException() {
    }

    public InvalidBinException(String message) {
        super(message);
    }

    public InvalidBinException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBinException(Throwable cause) {
        super(cause);
    }

    public InvalidBinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
