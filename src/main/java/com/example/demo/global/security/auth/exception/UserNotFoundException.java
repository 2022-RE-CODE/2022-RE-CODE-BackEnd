package com.example.demo.global.security.auth.exception;

import com.example.demo.global.error.exception.RecodeException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.demo.global.error.exception.ErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends RecodeException {

    public static final RecodeException EXCEPTION =
            new UserNotFoundException();

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
