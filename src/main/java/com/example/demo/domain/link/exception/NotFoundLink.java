package com.example.demo.domain.link.exception;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */
public class NotFoundLink extends CustomException {

    public static CustomException EXCEPTION = new NotFoundLink();

    public NotFoundLink() {
        super(ErrorCode.LINK_NOT_FOUND);
    }
}
