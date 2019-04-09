package com.scrap.culqui.exception;

import org.springframework.http.HttpStatus;

/**
 * CulquiException.
 *
 * @author Luis Alonso Ballena Garcia
 */

public class CulquiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String customMessage;

    public CulquiException(HttpStatus httpStatus, String customMessage) {
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }

    public CulquiException(String message, HttpStatus httpStatus, String customMessage) {
        super(message);
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }

    public CulquiException(String message, Throwable cause, HttpStatus httpStatus, String customMessage) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }

    public CulquiException(Throwable cause, HttpStatus httpStatus, String customMessage) {
        super(cause);
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }

    public CulquiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, String customMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
