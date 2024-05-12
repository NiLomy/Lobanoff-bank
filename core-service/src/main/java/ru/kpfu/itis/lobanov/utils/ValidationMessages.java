package ru.kpfu.itis.lobanov.utils;

public final class ValidationMessages {
    public static final String JSON_NOT_NULL = "Json string cannot be null";
    public static final String JSON_NOT_BLANK = "Json string shouldn't be empty";
    public static final String CURRENCY_CODE_NOT_NULL = "Currency code cannot be null";
    public static final String CURRENCY_CODE_NOT_BLANK = "Currency code shouldn't be empty";
    public static final String AMOUNT_NOT_NULL = "Amount of money to convert cannot be null";
    public static final String ID_NOT_NULL = "Id should not be null";
    public static final String ID_POSITIVE_OR_ZERO = "Is should not be negative";
    public static final String DATE_NOT_NULL = "Date cannot be null";
    public static final String DATE_NOT_BLANK = "Date should not be empty";
    public static final String DATE_SIZE = "Date should have proper size (min is 4 (0101) and max is 10 (01.01.0101))";
    public static final String PASSPORT_NOT_NULL = "Passport request cannot be null";

    private ValidationMessages() {}
}
