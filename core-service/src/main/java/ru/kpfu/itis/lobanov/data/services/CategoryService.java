package ru.kpfu.itis.lobanov.data.services;

import java.util.Map;

public interface CategoryService {
    Map<String, String> getAll();

    void uploadCategories();
}
