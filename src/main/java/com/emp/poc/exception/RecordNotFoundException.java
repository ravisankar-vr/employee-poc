package com.emp.poc.exception;


public class RecordNotFoundException extends RuntimeException{
    String message;
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
