package ru.kpfu.itis.lobanov.notificationservice.exceptions;

public class SendingVerificationCodeOnEmailException extends RuntimeException {
    public SendingVerificationCodeOnEmailException() {
    }

    public SendingVerificationCodeOnEmailException(String message) {
        super(message);
    }

    public SendingVerificationCodeOnEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendingVerificationCodeOnEmailException(Throwable cause) {
        super(cause);
    }

    public SendingVerificationCodeOnEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
