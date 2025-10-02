package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BinaryContent implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private UUID id;
  private Instant createdAt;
  private String fileName;
  private Long size;
  private String contentType;
  private byte[] bytes;

  public BinaryContent(String fileName, Long size, String contentType, byte[] bytes) {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    //
    this.fileName = fileName;
    this.size = size;
    this.contentType = contentType;
    this.bytes = bytes;
  }

  public BinaryContent(byte[] bytes) {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    //
    this.bytes = bytes;
  }

  public void update(String fileName, Long size, String contentType, byte[] bytes) {
    this.fileName = fileName;
    this.size = size;
    this.contentType = contentType;
    this.bytes = bytes;
  }
}
