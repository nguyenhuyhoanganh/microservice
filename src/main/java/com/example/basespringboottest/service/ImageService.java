package com.example.basespringboottest.service;

import com.example.basespringboottest.dto.response.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  public ImageResponse save(MultipartFile file);
}
