package com.example.basespringboottest.exception.base;

import static com.example.basespringboottest.constant.ExceptionCode.NOT_FOUND_CODE;
import static com.example.basespringboottest.exception.base.StatusConstants.NOT_FOUND;

/**
 * NotFoundException is a type of exception commonly
 * used to indicate that a resource or object cannot be found.
 */
public class NotFoundException extends BaseException {

    public NotFoundException() {
        setCode(NOT_FOUND_CODE);
        setStatus(NOT_FOUND);
    }
}
