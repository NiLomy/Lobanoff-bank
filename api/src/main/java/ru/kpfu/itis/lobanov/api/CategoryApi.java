package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "Category Api", description = "Provides methods for categories")
@RequestMapping(path = "/api/v1/category", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CategoryApi {
    @Operation(summary = "Get all categories.", description = "Returns all categories.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Categories not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all")
    ResponseEntity<Map<String, String>> getAllCategories();

    @Operation(summary = "Upload all categories.", description = "Returns result message after categories uploading.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/upload")
    ResponseEntity<String> uploadCategories();
}
