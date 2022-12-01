package com.example.demo.global.exception;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;

public class EmailNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new EmailNotFoundException();

    public EmailNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
