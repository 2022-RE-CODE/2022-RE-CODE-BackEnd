package com.example.demo.global.security.jwt.exception;

import com.example.demo.global.error.exception.RecodeException;
import com.example.demo.global.error.exception.ErrorCode;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public class InvalidJwtException extends RecodeException {

    public static final RecodeException EXCEPTION = new InvalidJwtException();

    public InvalidJwtException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}