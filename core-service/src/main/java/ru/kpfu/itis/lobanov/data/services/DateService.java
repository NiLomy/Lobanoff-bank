package ru.kpfu.itis.lobanov.data.services;

import java.time.LocalDate;

public interface DateService {
    String getCurrentDate();

    LocalDate getFullDate(String date);

    LocalDate getNextMonth(String date);

    LocalDate getPreviousMonth(String date);

    String formatDate(LocalDate date);
}
