package com.example.basespringboottest.service;

import com.example.basespringboottest.dto.base.PageResponse;
import com.example.basespringboottest.dto.response.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  public ImageResponse save(MultipartFile file);

  public ImageResponse getImage(String id);

  PageResponse<ImageResponse> list(int size, int page);

  PageResponse<ImageResponse> search(int size, int page, String keyword);

  public void delete(String id);

//  public byte[] getFile(String storagePath);
}
