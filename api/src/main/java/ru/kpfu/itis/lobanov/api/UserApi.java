package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface UserApi {
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable("id") String userId);

    @GetMapping("/current")
    ResponseEntity<UserDto> getCurrentUser();

    @GetMapping("/all")
    ResponseEntity<List<UserDto>> getAllUsers();

    @PostMapping("/ban/{id}")
    ResponseEntity<UserDto> banUser(@PathVariable("id") String userId);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<UserDto> deleteUser(@PathVariable("id") String userId);
}
