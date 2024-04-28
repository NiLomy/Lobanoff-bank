package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.CategoryApi;
import ru.kpfu.itis.lobanov.data.services.CategoryService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Map<String, String>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @Override
    public String uploadCategories() {
        categoryService.uploadCategories();
        return "success";
    }
}
