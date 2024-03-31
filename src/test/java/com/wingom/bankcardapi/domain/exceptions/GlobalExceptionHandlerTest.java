package com.wingom.bankcardapi.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    @Test
    public void handleEntityNotFoundExceptionTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        EntityNotFoundException ex = new EntityNotFoundException("Test exception message");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleEntityNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Test exception message");
    }

    @Test
    public void handleIllegalParamExceptionTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        IllegalParamException ex = new IllegalParamException("Test illegal param message");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalParamException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Test illegal param message");
    }

    @Test
    public void handleIllegalStateExceptionTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        java.lang.IllegalStateException ex = new java.lang.IllegalStateException("Test illegal state message");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalStateException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Test illegal state message");
    }

    @Test
    public void handleInsufficientFundsExceptionTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        InsufficientFundsException ex = new InsufficientFundsException("Test insufficient funds message");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInsufficientFundsException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Test insufficient funds message");
    }

    @Test
    public void handleIllegalCurrencyExceptionTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        IllegalCurrencyException ex = new IllegalCurrencyException("Test illegal currency message");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalCurrencyException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Test illegal currency message");
    }

}

