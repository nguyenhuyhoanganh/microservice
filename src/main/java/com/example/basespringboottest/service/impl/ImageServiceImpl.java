package com.example.basespringboottest.service.impl;

import com.example.basespringboottest.dto.response.image.ImageResponse;
import com.example.basespringboottest.entity.Image;
import com.example.basespringboottest.repository.ImageRepository;
import com.example.basespringboottest.service.ImageService;
import com.example.basespringboottest.service.StorageService;
import com.example.basespringboottest.utils.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
  private final ImageRepository repository;
  private final ImageUtils imageUtils;
  private final StorageService storageService;

  @Transactional
  @Override
  public ImageResponse save(MultipartFile file) {
    imageUtils.validateFile(file);

    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String storagePath = storageService.storeFile(file, fileName);

    Image image = new Image(storagePath, file.getContentType(), fileName);
    image = repository.save(image);
    return new ImageResponse(image.getId(), storagePath, image.getContentType(), image.getName());
  }

}
