package com.example.demo.domain.exception;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;

public class RefreshTokenNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new RefreshTokenNotFoundException();

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
