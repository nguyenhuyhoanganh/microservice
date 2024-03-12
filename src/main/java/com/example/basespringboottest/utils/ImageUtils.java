package com.example.basespringboottest.utils;

import com.example.basespringboottest.exception.image.FileTooLargeException;
import com.example.basespringboottest.exception.image.ImageEmptyException;
import com.example.basespringboottest.exception.image.ImageFormatInCorrectException;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.basespringboottest.constant.CommonConstants.*;

@Slf4j
@Component
public class ImageUtils {
  /**
   * Validate file
   */
  public void validateFile(MultipartFile file) {

    /**
     * Check name of file
     */
    String fileName = file.getOriginalFilename();
    if (fileName == null || fileName.isEmpty()) {
      throw new ImageEmptyException();
    }

    /**
     *check the file extension
     */
    String fileExtension = getFileExtension(fileName);
    if (!checkFileExtension(fileExtension)) {
      throw new ImageFormatInCorrectException();
    }

    /**
     * Check size of file
     */
    double fileSize = (double) (file.getSize() / BYTES_IN_MEGABYTE);
    if (fileSize > MAX_FILE_SIZE_MB) {
      throw new FileTooLargeException();
    }
  }

  /**
   * Get the file extension (example png, jpg, ...)
   */
  public String getFileExtension(String fileName) {
    int lastIndexOf = fileName.lastIndexOf(LAST_INDEX_OF);
    return fileName.substring(lastIndexOf + 1);
  }

  /**
   * Check extension of file (example png, jpg, jpeg, pdf)
   */
  public boolean checkFileExtension(String fileExtension) {
    List<String> extensions = new ArrayList<>(List.of(EXTENSION_PNG, EXTENSION_JPG, EXTENSION_JPEG, EXTENSION_PDF));
    return extensions.contains(fileExtension.toLowerCase());
  }


}