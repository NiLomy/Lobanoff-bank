package ru.kpfu.itis.lobanov.data.services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.Category;
import ru.kpfu.itis.lobanov.data.repositories.CategoryRepository;
import ru.kpfu.itis.lobanov.data.services.CategoryService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Map<String, String> getAll() {
        return categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getTitle, Category::getIcon));
    }

    @Override
    public void uploadCategories() {
        try (FileReader reader = new FileReader("core-service/src/main/resources/static/categories.json")) {
            JsonArray json = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : json.asList()) {
                JsonObject data = element.getAsJsonObject();
                categoryRepository.save(
                        Category.builder()
                                .title(data.get("title").getAsString())
                                .icon(data.get("svg").getAsString())
                                .build()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
