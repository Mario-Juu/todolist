package com.mariojunior.todo.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DataBindingViolationException extends DataIntegrityViolationException {
    public DataBindingViolationException(String msg) {
        super(msg);
    }

    public DataBindingViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
