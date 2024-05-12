package ru.kpfu.itis.lobanov.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateProvider {
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public LocalDate getFullDate(String date) {
        String[] values = date.split("-");
        return LocalDate.of(Integer.parseInt(values[1]), Integer.parseInt(values[0]), 1);
    }

    public LocalDate getNextMonth(String date) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.plusMonths(1);
    }

    public LocalDate getPreviousMonth(String date) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.minusMonths(1);
    }

    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public Date parseDate(String date) {
        String[] values = date.split("\\.");
        LocalDateTime localDate = LocalDateTime.of(
                Integer.parseInt(values[2]), Integer.parseInt(values[1]), Integer.parseInt(values[0]), 0 ,0
        );
        Instant dateInstant = localDate.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(dateInstant);
    }
}