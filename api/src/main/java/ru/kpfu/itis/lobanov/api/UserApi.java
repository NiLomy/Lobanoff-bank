package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.requests.UpdateUserRequest;

import java.util.List;

@Tag(name = "User Api", description = "Provides methods to manage users")
@RequestMapping(path = "/api/v1/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface UserApi {
    @Operation(summary = "Get user by id.", description = "Returns user by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(
            @Parameter(description = "Id of the user.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Get all users.", description = "Returns all users.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Users not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all")
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(summary = "Get admin.", description = "Returns admin.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Admin not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/admin")
    ResponseEntity<UserDto> getAdmin();

    @Operation(summary = "Update user.", description = "Returns updated user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PutMapping("/update")
    ResponseEntity<UserDto> updateUser(
            @Parameter(description = "Request for updating user.", required = true)
            @RequestBody
            UpdateUserRequest request
    );

    @Operation(summary = "Ban user.", description = "Returns banned user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/ban/{id}")
    ResponseEntity<UserDto> banUser(
            @Parameter(description = "Id of the user.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Delete user.", description = "Returns deleted user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<UserDto> deleteUser(
            @Parameter(description = "Id of the user.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );
}
