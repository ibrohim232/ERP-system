package com.example.erpsystem.exception;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(String formatted) {
        super(formatted);
    }
}
