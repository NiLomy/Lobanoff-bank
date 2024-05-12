package ru.kpfu.itis.lobanov.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

import static ru.kpfu.itis.lobanov.utils.BankingConstants.*;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {
    public static final String CONTROL_KEY_PLACEHOLDER = "0";
    public static final int NUM_OF_CHARS_TO_USE = 3;
    public static final int PERSONAL_ACCOUNT_NUMBER_LOWER_BOUND = 1000000;
    public static final int PERSONAL_ACCOUNT_NUMBER_UPPER_BOUND = 9000000;
    public static final int LAST_CHAR_DIVIDER = 10;
    public static final int DIGIT_SUM_MULTIPLIER = 3;
    public static final int CORRESPONDENT_ACCOUNT_NUMBER_LOWER_BOUND = 100000000;
    public static final int CORRESPONDENT_ACCOUNT_NUMBER_UPPER_BOUND = 900000000;
    private final Random random;

    public String generatePersonalAccountNumber() {
        String organizationNumber = BANK_IDENTIFICATION_CODE.substring(
                BANK_IDENTIFICATION_CODE.length() - NUM_OF_CHARS_TO_USE);
        String personalAccountNumber = BALANCE_ACCOUNT_OF_AN_INDIVIDUAL + CURRENCY_CODE_OF_THE_RUSSIAN_RUBLE +
                CONTROL_KEY_PLACEHOLDER + BANKING_DIVISION + (PERSONAL_ACCOUNT_NUMBER_LOWER_BOUND +
                random.nextInt(PERSONAL_ACCOUNT_NUMBER_UPPER_BOUND));
        String mask = organizationNumber + personalAccountNumber;
        int lowestDigitsSum = 0;
        for (int i = 0; i < WEIGHTS.length(); i++) {
            lowestDigitsSum += (Integer.parseInt(String.valueOf(mask.charAt(i))) *
                    Integer.parseInt(String.valueOf(WEIGHTS.charAt(i)))) % LAST_CHAR_DIVIDER;
        }
        int controlKey = ((lowestDigitsSum % LAST_CHAR_DIVIDER) * DIGIT_SUM_MULTIPLIER) % LAST_CHAR_DIVIDER;
        return BALANCE_ACCOUNT_OF_AN_INDIVIDUAL + CURRENCY_CODE_OF_THE_RUSSIAN_RUBLE + controlKey + BANKING_DIVISION +
                (PERSONAL_ACCOUNT_NUMBER_LOWER_BOUND + random.nextInt(PERSONAL_ACCOUNT_NUMBER_UPPER_BOUND));
    }

    public String generateCorrespondentAccountNumber() {
        return FIRST_RUSSIAN_CORRESPONDENT_NUMBERS + SECOND_RUSSIAN_CORRESPONDENT_NUMBERS +
                CURRENCY_CODE_OF_THE_RUSSIAN_RUBLE +
                (CORRESPONDENT_ACCOUNT_NUMBER_LOWER_BOUND + random.nextLong(CORRESPONDENT_ACCOUNT_NUMBER_UPPER_BOUND)) +
                BANK_IDENTIFICATION_CODE.substring(BANK_IDENTIFICATION_CODE.length() - NUM_OF_CHARS_TO_USE);
    }
}
