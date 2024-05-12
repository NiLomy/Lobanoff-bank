package ru.kpfu.itis.lobanov.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.DATE_NOT_BLANK;
import static ru.kpfu.itis.lobanov.utils.ValidationMessages.DATE_NOT_NULL;

@Validated
@Component
public class DateProvider {
    public static final String MONTH_AND_YEAR_MASK = "MM-yyyy";
    public static final String FULL_DATE_MASK = "dd.MM.yyyy";
    public static final String MINUS_DELIMITER = "-";
    public static final String DOT_DELIMITER = "\\.";

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(MONTH_AND_YEAR_MASK);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public LocalDate getFullDate(
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            String date
    ) {
        final int yearIndex = 1;
        final int monthIndex = 0;
        final int dayOfMonth = 1;
        String[] values = date.split(MINUS_DELIMITER);
        return LocalDate.of(Integer.parseInt(values[yearIndex]), Integer.parseInt(values[monthIndex]), dayOfMonth);
    }

    public LocalDate getNextMonth(
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            String date
    ) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.plusMonths(1);
    }

    public LocalDate getPreviousMonth(
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            String date
    ) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.minusMonths(1);
    }

    public String formatDate(
            @NotNull(message = DATE_NOT_NULL)
            LocalDate date
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FULL_DATE_MASK);
        return date.format(formatter);
    }

    public Date parseDate(
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            String date
    ) {
        final int yearIndex = 2;
        final int monthIndex = 1;
        final int dayIndex = 0;
        String[] values = date.split(DOT_DELIMITER);
        LocalDateTime localDate = LocalDateTime.of(
                Integer.parseInt(values[yearIndex]),
                Integer.parseInt(values[monthIndex]),
                Integer.parseInt(values[dayIndex]),
                0,
                0
        );
        Instant dateInstant = localDate.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(dateInstant);
    }
}
