package com.aurea.jacoco;

public class ErrorInfo {
    private final String requestURL;
    private final String error;

    public ErrorInfo(String requestURL, Exception cause) {
        this.requestURL = requestURL;
        this.error = cause.getLocalizedMessage();
    }

    public String getError() {
        return error;
    }

    public String getRequestURL() {
        return requestURL;
    }
}
