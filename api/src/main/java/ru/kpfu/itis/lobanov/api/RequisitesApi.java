package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

@Tag(name = "Requisites Api", description = "Provides methods for bank requisites")
@RequestMapping(path = "/api/v1/requisites", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface RequisitesApi {
    @Operation(summary = "Get requisites by id.", description = "Returns user's requisites by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Requisites not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<RequisitesDto> getRequisitesById(
            @Parameter(description = "Id of the user's requisites.", example = "123", required = true)
            @PathVariable("id")
            String requisitesId
    );

    @Operation(summary = "Get requisites by account id.", description = "Returns user's requisites by provided account id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Requisites not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/account/{id}")
    ResponseEntity<RequisitesDto> getRequisitesByAccountId(
            @Parameter(description = "Id of the user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId
    );
}
