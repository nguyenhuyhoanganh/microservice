package com.example.basespringboottest.controller;

import com.example.basespringboottest.dto.base.PageResponse;
import com.example.basespringboottest.dto.base.ResponseGeneral;
import com.example.basespringboottest.dto.response.image.ImageResponse;
import com.example.basespringboottest.service.ImageService;
import com.example.basespringboottest.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.basespringboottest.constant.CommonConstants.*;
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
  public ResponseGeneral<ImageResponse> uploadImage(
        @RequestParam("files") MultipartFile files,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(uploadImage) MultipartFile: {}", files);

    return ResponseGeneral.ofCreated(
          messageService.getMessage(SUCCESS, language), service.save(files)
    );
  }

  @GetMapping("{id}")
  public ResponseGeneral<ImageResponse> getImage(
        @PathVariable("id") String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(getImage) id: {}", id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(SUCCESS, language),
          service.getImage(id)
    );
  }

  @GetMapping("/all")
  public ResponseGeneral<PageResponse<ImageResponse>> list(
        @RequestParam(name = SIZE, defaultValue = DEFAULT_SIZE) int size,
        @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) size : {}, page: {}", size, page);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(SUCCESS, language),
          service.list(size, page)
    );
  }

  @GetMapping("/search")
  public ResponseGeneral<PageResponse<ImageResponse>> listSearch(
        @RequestParam(name = KEYWORD, required = false) String keyword,
        @RequestParam(name = SIZE, defaultValue = DEFAULT_SIZE) int size,
        @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) size : {}, page: {}", size, page);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(SUCCESS, language),
          service.search(size, page, keyword)
    );
  }

  @DeleteMapping("{id}")
  public ResponseGeneral<Void> delelte(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(delete) id: {}", id);

    service.delete(id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(SUCCESS, language)
    );
  }
}
