package com.example.restvotingapp.exceptions;

public class WrongRequest extends RuntimeException {

    public WrongRequest(String message) {
        super(message);
    }
}
