package com.example.erpsystem.exception;

public class WrongInputException extends RuntimeException {
    public WrongInputException(String msg) {
        super(msg);
    }
}
