package ru.kpfu.itis.lobanov.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CardNumberMaskingSerializer extends JsonSerializer<String> {
    public static final String ALL_NUMBERS_EXCEPT_FOUR_LAST_REGEX = ".(?=.{4})";
    public static final String MASKING_SYMBOL = "*";

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        value = value.replaceAll(ALL_NUMBERS_EXCEPT_FOUR_LAST_REGEX, MASKING_SYMBOL);
        gen.writeString(value);
    }
}
