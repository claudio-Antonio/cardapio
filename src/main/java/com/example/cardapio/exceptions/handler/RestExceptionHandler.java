package com.example.cardapio.exceptions.handler;

import com.example.cardapio.exceptions.BadRequestException;
import com.example.cardapio.exceptions.BadRequestExceptionsDetails;
import com.example.cardapio.exceptions.NotFoundException;
import com.example.cardapio.exceptions.NotFoundExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<BadRequestExceptionsDetails> handleBadRequestException(BadRequestException exception) {
        BadRequestExceptionsDetails bre = BadRequestExceptionsDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Bad Request exception, check the details.")
                .details(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<NotFoundExceptionDetails>  handleNotFoundException(NotFoundException exception) {
        NotFoundExceptionDetails nfe = NotFoundExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message("Not found exception, check de root or json body.")
                .details(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nfe);
    }
}
