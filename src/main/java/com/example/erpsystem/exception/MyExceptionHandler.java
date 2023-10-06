package com.example.erpsystem.exception;


import com.example.erpsystem.dto.base.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorDto> myExceptionHandler(DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage(), 401));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDto> bindExceptionHandler(BindException e) {
        StringBuilder massages = new StringBuilder();
        e.getAllErrors().forEach(error -> {
            massages.append(error.getDefaultMessage());
            massages.append(" , ");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(massages.toString(), 404));
    }
}
