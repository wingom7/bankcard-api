package com.wingom.bankcardapi.domain.exceptions;

public class IllegalCurrencyException extends RuntimeException {
    public IllegalCurrencyException(String message) {
        super(message);
    }
}
