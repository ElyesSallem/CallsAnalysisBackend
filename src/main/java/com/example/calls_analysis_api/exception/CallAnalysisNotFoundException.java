package com.example.calls_analysis_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Call analysis not found")
public class CallAnalysisNotFoundException extends RuntimeException {
    public CallAnalysisNotFoundException(String message) {
        super(message);
    }
}
