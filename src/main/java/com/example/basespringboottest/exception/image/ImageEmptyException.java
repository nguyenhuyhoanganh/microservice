package com.example.basespringboottest.exception.image;

import com.example.basespringboottest.exception.base.BadRequestException;

import static com.example.basespringboottest.constant.ExceptionCode.IMAGE_EMPTY;

public class ImageEmptyException extends BadRequestException {
  public ImageEmptyException(){
    setCode("com.example.basespringboottest.exception.image.ImageEmptyException");
  }
}
