package com.example.springmvvm.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
    private CodeException codeException;

    public AppException(CodeException codeException) {
        this.codeException = codeException;
    }
}
