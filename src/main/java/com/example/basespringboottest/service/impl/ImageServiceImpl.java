package com.example.basespringboottest.service.impl;

import com.example.basespringboottest.dto.base.PageResponse;
import com.example.basespringboottest.dto.response.image.ImageResponse;
import com.example.basespringboottest.entity.Image;
import com.example.basespringboottest.exception.image.ImageNotFoundException;
import com.example.basespringboottest.repository.ImageRepository;
import com.example.basespringboottest.service.ImageService;
import com.example.basespringboottest.service.StorageService;
import com.example.basespringboottest.utils.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

import static com.example.basespringboottest.constant.CommonConstants.LOW_LINE;

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
    log.info("(save) file: {}", file);

    String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
    String cleanFilename = StringUtils.cleanPath(originalFilename);
    String storagePath;

    storagePath = storageService.storeFile(file,
          UUID.randomUUID().toString() + LOW_LINE + cleanFilename);

    Image image = new Image(storagePath, file.getContentType(), cleanFilename);
    image = repository.save(image);

    return ImageResponse.builder()
          .id(image.getId())
          .url(storagePath)
          .contentType(image.getContentType())
          .name(cleanFilename)
          .build();
  }

  @Override
  public ImageResponse getImage(String id) {
    Image image = repository.findById(id).orElseThrow(ImageNotFoundException::new);

    return new ImageResponse(
          image.getId(),
          image.getUrl(),
          image.getContentType(),
          image.getName()
    );
  }

  @Override
  public PageResponse<ImageResponse> list(int size, int page) {
    log.info("(list):, size : {}, page: {}", size, page);

    Pageable pageable = PageRequest.of(page, size);
    Page<ImageResponse> pageResponse = repository.findAllImage(pageable);

    return PageResponse.of(pageResponse.getContent(), (int) pageResponse.getTotalElements());
  }

  @Override
  public PageResponse<ImageResponse> search(int size, int page, String keyword) {
    log.info("(list):, size : {}, page: {}, keyword: {}", size, page, keyword);

    Pageable pageable = PageRequest.of(page, size);
    Page<ImageResponse> pageResponse = repository.search(pageable, keyword);

    return PageResponse.of(pageResponse.getContent(), (int) pageResponse.getTotalElements());
  }

  @Override
  public void delete(String id) {
    log.info("(delete) id: {}", id);

    Image image = repository.findById(id).orElseThrow(ImageNotFoundException::new);
    repository.delete(image);
  }


}
