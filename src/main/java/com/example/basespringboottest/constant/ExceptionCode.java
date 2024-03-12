package com.example.basespringboottest.constant;

/**
 * Constants representing exception codes used in the application.
 * These codes can be associated with specific types of exceptions for identification and handling.
 */
public class ExceptionCode {
    public static final String BAD_REQUEST_CODE = "com.example.springproject.exception.base.BadRequestException";
    public static final String CONFLICT_CODE = "com.example.springproject.exception.base.ConflictException";
    public static final String NOT_FOUND_CODE = "com.example.springproject.exception.base.NotFoundException";
    public static final String GENERIC_CODE = "com.example.springproject.exception.base.GenericException";
    public static final String IMAGE_NOTFOUND= "com.example.basespringboottest.exception.image.ImageNotFoundException";
    public static final String IMAGE_EMPTY= "com.example.basespringboottest.exception.image.ImageEmptyException";
}
