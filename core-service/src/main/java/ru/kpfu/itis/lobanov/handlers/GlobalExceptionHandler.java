package ru.kpfu.itis.lobanov.handlers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kpfu.itis.lobanov.dtos.responses.ValidationErrorResponse;
import ru.kpfu.itis.lobanov.dtos.responses.Violation;
import ru.kpfu.itis.lobanov.exceptions.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            NullPointerException.class,
            IllegalArgumentException.class,
            InvalidAmountOfTransactionException.class,
            InvalidBinException.class,
            UserNotFoundException.class,
            AccountNotFoundException.class,
            PasswordMatchException.class,
            EmailAlreadyInUseException.class,
            PhoneAlreadyInUseException.class,
            RequisitesNotFoundException.class,
            PassportAlreadyInUseException.class,
            PassportNotFoundException.class,
            ApiException.class
    })
    public ErrorResponse handleAuthenticationException(RuntimeException ex) {
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()
                .substring(ex.getMessage().indexOf(" ")).trim()).build();
    }

    @ExceptionHandler({
            TransactionTypeNotFoundException.class,
            TransactionMethodNotFoundException.class,
            BankBinNotFoundException.class
    })
    public ErrorResponse handleTransactionException(RuntimeException ex) {
        return ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
                .substring(ex.getMessage().indexOf(" ")).trim()).build();
    }

    @ExceptionHandler({
            NotEnoughMoneyException.class
    })
    public ErrorResponse handleNotEnoughMoneyException(RuntimeException ex) {
        return ErrorResponse.builder(ex, HttpStatus.FORBIDDEN, ex.getMessage()
                .substring(ex.getMessage().indexOf(" ")).trim()).build();
    }

    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Violation> violations = ex.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                ).toList();
        return new ResponseEntity<>(new ValidationErrorResponse(violations), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(
                        error -> new Violation(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                ).toList();
        return new ResponseEntity<>(new ValidationErrorResponse(violations), HttpStatus.BAD_REQUEST);
    }
}
