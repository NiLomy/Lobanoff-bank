package ru.kpfu.itis.lobanov.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kpfu.itis.lobanov.exceptions.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, InvalidAmountOfTransactionException.class, InvalidBinException.class})
    public ErrorResponse handleAuthenticationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage().substring(ex.getMessage().indexOf(" ")).trim()).build();
    }

    @ExceptionHandler({TransactionTypeNotFoundException.class, TransactionMethodNotFoundException.class, BankBinNotFoundException.class})
    public ErrorResponse handleTransactionException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        return ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage().substring(ex.getMessage().indexOf(" ")).trim()).build();
    }

    @ExceptionHandler({NotEnoughMoneyException.class})
    public ErrorResponse handleNotEnoughMoneyException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        return ErrorResponse.builder(ex, HttpStatus.FORBIDDEN, ex.getMessage().substring(ex.getMessage().indexOf(" ")).trim()).build();
    }
}
