package com.example.basespringboottest.exception.base;

import static com.example.basespringboottest.constant.ExceptionCode.BAD_REQUEST_CODE;
import static com.example.basespringboottest.exception.base.StatusConstants.BAD_REQUEST;

/**
 * BadRequestException is a type of exception commonly
 * to indicate that a client's request to a server is malformed or invalid
 */
public class BadRequestException extends BaseException {

    public BadRequestException() {
        setCode(BAD_REQUEST_CODE);
        setStatus(BAD_REQUEST);
    }
}
