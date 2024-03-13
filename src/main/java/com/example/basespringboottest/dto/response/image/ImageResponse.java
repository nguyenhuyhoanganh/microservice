package com.example.basespringboottest.dto.response.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
  private String id;
  private String url;
  private String contentType;
  private String name;
  private byte[] data;

  public ImageResponse(String id, String url, String contentType, String name) {
    this.id = id;
    this.url = url;
    this.contentType = contentType;
    this.name = name;
  }
}
