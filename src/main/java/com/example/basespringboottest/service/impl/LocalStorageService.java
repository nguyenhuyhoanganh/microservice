package com.example.basespringboottest.service.impl;

import com.example.basespringboottest.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalStorageService implements StorageService {
  private final String storagePath;

  public LocalStorageService(String storagePath) {
    this.storagePath = storagePath;
  }

  @Override
  public String storeFile(MultipartFile file, String fileName) {
    try {
      Path filePath = Paths.get(storagePath, fileName);
      Files.write(filePath, file.getBytes());
      return filePath.toString();
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while storing the file", e);
    }
  }

//  @Override
//  public String storeFile(byte[] data, String fileName) {
//    try {
//      Path filePath = Paths.get(storagePath, fileName);
//      Files.write(filePath, data);
//      return filePath.toString();
//    } catch (IOException e) {
//      throw new RuntimeException("Lỗi lưu trữ tệp cục bộ", e);
//    }
//  }


}



