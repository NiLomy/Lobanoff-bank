package ru.kpfu.itis.lobanov.data.services.impl;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.services.DateService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public LocalDate getFullDate(String date) {
        String[] values = date.split("-");
        return LocalDate.of(Integer.parseInt(values[1]), Integer.parseInt(values[0]), 1);
    }

    @Override
    public LocalDate getNextMonth(String date) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.plusMonths(1);
    }

    @Override
    public LocalDate getPreviousMonth(String date) {
        LocalDate dateFrom = getFullDate(date);
        return dateFrom.minusMonths(1);
    }

    @Override
    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}
