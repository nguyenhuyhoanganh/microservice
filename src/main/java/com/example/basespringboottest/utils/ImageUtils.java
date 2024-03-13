package com.example.basespringboottest.utils;

import com.example.basespringboottest.exception.image.FileTooLargeException;
import com.example.basespringboottest.exception.image.ImageEmptyException;
import com.example.basespringboottest.exception.image.ImageFormatInCorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
    log.info("size of the file: {}", fileSize);
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

  public static byte[] compressImage(byte[] data) throws IOException {
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[BITE_SIZE];

    while (!deflater.finished()) {
      int size = deflater.deflate(tmp);
      outputStream.write(tmp, 0, size);
    }

    outputStream.close();

    return outputStream.toByteArray();
  }

  public static byte[] decompressImage(byte[] data) throws DataFormatException, IOException {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[BITE_SIZE];

    while (!inflater.finished()) {
      int count = inflater.inflate(tmp);
      outputStream.write(tmp, 0, count);
    }

    outputStream.close();

    return outputStream.toByteArray();
  }

}