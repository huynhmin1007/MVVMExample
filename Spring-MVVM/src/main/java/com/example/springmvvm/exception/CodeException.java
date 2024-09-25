package com.example.springmvvm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public enum CodeException {

    USER_NOT_FOUND(404, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    private static final Map<CodeException, AppException> EXCEPTIONS = new ConcurrentHashMap<>();

    public AppException throwException() {
        return EXCEPTIONS.computeIfAbsent(this, AppException::new);
    }
}