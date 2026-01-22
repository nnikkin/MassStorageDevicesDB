package com.nikkin.devicesdb.Exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

// класс ошибки API
@Getter
public class ApiError {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiError(String message) {
        this.message = message;
    }

    public void setMessage(String message) { this.message = message; }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}