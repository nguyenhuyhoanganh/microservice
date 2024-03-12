package com.example.basespringboottest.exception.image;

import com.example.basespringboottest.exception.base.BadRequestException;

public class ImageFormatInCorrectException extends BadRequestException {
  public ImageFormatInCorrectException(){
    setCode("com.example.basespringboottest.exception.image.ImageFormatInCorrectException");
  }
}
