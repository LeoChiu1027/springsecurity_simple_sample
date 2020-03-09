package com.armi.sample.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String timestamp;
    private String status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(String timestamp, String status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse() {
    }
}
