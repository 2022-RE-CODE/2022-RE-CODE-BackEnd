package com.example.demo.global.exception;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;

public class InvalidJwtException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidJwtException();

    public InvalidJwtException() {
        super(ErrorCode.INVALID_JWT);
    }
}
