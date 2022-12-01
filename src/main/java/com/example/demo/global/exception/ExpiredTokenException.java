package com.example.demo.global.exception;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {

    public final static ExpiredTokenException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_JWT);
    }

}
