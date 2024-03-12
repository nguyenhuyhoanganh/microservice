package com.example.basespringboottest.exception.image;

import com.example.basespringboottest.exception.base.BadRequestException;

public class FileTooLargeException extends BadRequestException {
  public FileTooLargeException(){
    setCode("com.example.basespringboottest.exception.image.FileTooLargeException");
  }
}
