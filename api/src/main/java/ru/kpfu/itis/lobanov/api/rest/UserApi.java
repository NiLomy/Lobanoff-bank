package ru.kpfu.itis.lobanov.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@RequestMapping("/api")
public interface UserApi {
    @Operation(summary = "Getting all users.")
    @ApiResponse(responseCode = "200", description = "Successfully get all users.")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers();

    @Operation(summary = "Get request body", description = "Get request body")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId);
}
