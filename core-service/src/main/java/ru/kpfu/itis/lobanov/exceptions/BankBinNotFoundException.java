package ru.kpfu.itis.lobanov.exceptions;

public class BankBinNotFoundException extends RuntimeException {
    public BankBinNotFoundException() {
    }

    public BankBinNotFoundException(String message) {
        super(message);
    }

    public BankBinNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankBinNotFoundException(Throwable cause) {
        super(cause);
    }

    public BankBinNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
