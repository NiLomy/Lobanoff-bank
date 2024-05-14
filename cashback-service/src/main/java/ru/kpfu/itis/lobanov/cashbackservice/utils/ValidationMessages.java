package ru.kpfu.itis.lobanov.cashbackservice.utils;

public final class ValidationMessages {
    public static final String AMOUNT_NOT_NULL = "Amount of money to convert cannot be null";
    public static final String AMOUNT_POSITIVE_OR_ZERO = "Amount should not be negative";
    public static final String ID_NOT_NULL = "Id should not be null";
    public static final String ID_POSITIVE_OR_ZERO = "Is should not be negative";
    public static final String DATE_NOT_NULL = "Date cannot be null";
    public static final String TRANSACTION_NOT_NULL = "Transaction cannot be null";
    public static final String NAME_NOT_NULL = "Name cannot be null";
    public static final String NAME_NOT_BLANK = "Name should not be empty";
    public static final String SERVICE_COMPANY_NOT_NULL = "Service company cannot be null";
    public static final String ISO_CODE_NOT_NULL = "Iso code cannot be null";
    public static final String ISO_CODE_NOT_BLANK = "Iso code should not be empty";
    public static final String TITLE_NOT_NULL = "Title cannot be null";
    public static final String TITLE_NOT_BLANK = "Title should not be empty";


    private ValidationMessages() {}
}
