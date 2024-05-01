package ru.kpfu.itis.lobanov.utils;

public class ExceptionMessages {
    public static final String NOT_ENOUGH_MONEY_FOR_OPERATION = "There are not enough funds in your account to perform the operation.";
    public static final String NO_SUCH_TRANSACTION_METHOD = "There is no such transaction method.";
    public static final String NO_SUCH_TRANSACTION_TYPE = "There is no such transaction type.";
    public static final String NO_SUCH_BIN = "There is no such BIN.";
    public static final String INVALID_AMOUNT_OF_MONEY = "The amount of money in the transaction should be positive.";
    public static final String INVALID_SENDER_BANK_NAME = "Name of sender bank shouldn't be empty.";
    public static final String INVALID_RECEIVER_BANK_NAME = "Name of receiver bank shouldn't be empty.";

    private ExceptionMessages() {}
}
