package ru.kpfu.itis.lobanov.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Random;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.RANDOM_INSTANCE_WITH_BINDING;

@Validated
@Component
public class CreditCardNumberGenerator {
    @Autowired
    @Qualifier(RANDOM_INSTANCE_WITH_BINDING)
    private Random random;
    public static final int DIGITS_COUNT = 10;

    public String generate(String bin, int length) {
        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = random.nextInt(DIGITS_COUNT);
            builder.append(digit);
        }

        int checkDigit = getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }

    private int getCheckDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > DIGITS_COUNT - 1) {
                    digit = (digit / DIGITS_COUNT) + (digit % DIGITS_COUNT);
                }
            }
            sum += digit;
        }

        int mod = sum % DIGITS_COUNT;
        return ((mod == 0) ? 0 : DIGITS_COUNT - mod);
    }
}
