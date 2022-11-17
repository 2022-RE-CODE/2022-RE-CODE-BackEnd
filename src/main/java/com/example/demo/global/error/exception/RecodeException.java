package com.example.demo.global.error.exception;

import lombok.Getter;

@Getter
public class RecodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecodeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
