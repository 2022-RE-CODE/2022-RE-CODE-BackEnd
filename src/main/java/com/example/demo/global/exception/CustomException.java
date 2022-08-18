package com.example.demo.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends Throwable {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
