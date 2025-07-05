package com.tempus_api.exceptions;

public class ExceptionHandlerController extends RuntimeException {

    public ExceptionHandlerController(String message) {
        super(message);
    }
}
