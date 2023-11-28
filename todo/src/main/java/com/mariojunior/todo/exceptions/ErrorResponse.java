package com.mariojunior.todo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private List<ValidationErrors> errors;
    private String stackTrace;

    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }


    @Data
    @RequiredArgsConstructor
    private static class ValidationErrors {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if(Objects.isNull(errors)){
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationErrors(field, message));
    }
}
