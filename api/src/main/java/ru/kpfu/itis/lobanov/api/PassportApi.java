package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

@Tag(name = "Passport Api", description = "Provides methods for users passports")
@RequestMapping(path = "/api/v1/passports", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface PassportApi {
    @Operation(summary = "Get passport by id.", description = "Returns user's passport by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Passport not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<PassportDto> getPassportById(
            @Parameter(description = "Id of the current user's passport.", example = "123", required = true)
            @PathVariable("id")
            String passportId
    );

    @Operation(summary = "Save passport.", description = "Returns user's created passport.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Passport not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/save")
    ResponseEntity<PassportDto> savePassport(
            @Parameter(description = "Request for passport saving.", required = true)
            @RequestBody
            SavePassportRequest request
    );

    @Operation(summary = "Update passport.", description = "Returns user's updated passport.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Passport not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PutMapping("/update")
    ResponseEntity<PassportDto> updatePassport(
            @Parameter(description = "Request for passport updating.", required = true)
            @RequestBody
            UpdatePassportRequest request
    );
}
