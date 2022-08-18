package com.example.demo.global.jwt.exception;

import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {

    public final static ExpiredTokenException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }

}
