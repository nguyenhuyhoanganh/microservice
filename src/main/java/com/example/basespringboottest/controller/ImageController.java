package com.example.basespringboottest.controller;

import com.example.basespringboottest.dto.base.ResponseGeneral;
import com.example.basespringboottest.dto.response.image.ImageResponse;
import com.example.basespringboottest.service.ImageService;
import com.example.basespringboottest.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.basespringboottest.constant.CommonConstants.DEFAULT_LANGUAGE;
import static com.example.basespringboottest.constant.CommonConstants.LANGUAGE;
import static com.example.basespringboottest.constant.MessageCodeConstants.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
  private final MessageService messageService;
  private final ImageService service;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseGeneral<ImageResponse> upload(
        @RequestParam("files") MultipartFile files,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language) {

    log.info("(upload) MultipartFile: {}", files);
    return ResponseGeneral.ofCreated(
          messageService.getMessage(SUCCESS, language),
          service.save(files)
    );
  }

}
