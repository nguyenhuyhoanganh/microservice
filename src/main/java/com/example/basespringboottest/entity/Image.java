package com.example.basespringboottest.entity;

import com.example.basespringboottest.entity.base.BaseEntityWithUpdater;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")

public class Image extends BaseEntityWithUpdater {
  private String url;
  private String contentType;
  private String name;
  @Lob
  private byte[] data;
  private boolean isDeleted;

  public Image(String url, String contentType, String name) {
    this.url = url;
    this.contentType = contentType;
    this.name = name;
    this.isDeleted = false;
  }
}
