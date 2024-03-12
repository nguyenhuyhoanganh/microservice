package com.example.basespringboottest.exception.image;

import com.example.basespringboottest.exception.base.NotFoundException;

public class ImageNotFoundException extends NotFoundException {
  public ImageNotFoundException() {
    setCode("com.example.basespringboottest.exception.image.ImageNotFoundException");
  }
}
