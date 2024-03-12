package com.example.basespringboottest.configuration;

import com.example.basespringboottest.repository.ImageRepository;
import com.example.basespringboottest.service.ImageService;
import com.example.basespringboottest.service.StorageService;
import com.example.basespringboottest.service.base.MessageService;
import com.example.basespringboottest.service.base.MessageServiceImpl;
import com.example.basespringboottest.service.impl.ImageServiceImpl;
import com.example.basespringboottest.service.impl.LocalStorageService;
import com.example.basespringboottest.utils.ImageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.basespringboottest.constant.CommonConstants.STORAGE_PATH;

@Configuration
public class ServiceConfiguration {
  @Bean
  public MessageService messageService(MessageSource messageSource) {
    return new MessageServiceImpl(messageSource);
  }

  @Bean
  public StorageService storageService() {
    return new LocalStorageService(STORAGE_PATH);
  }

  @Bean
  public ImageService imageService(ImageRepository repository, ImageUtils imageUtils, StorageService storageService) {
    return new ImageServiceImpl(repository, imageUtils, storageService);
  }

}
