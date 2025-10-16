package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinaryContentMapper {

  private final BinaryContentStorage storage;

  public BinaryContentDto toDto(BinaryContent binaryContent) {
    try {
      return new BinaryContentDto(
          binaryContent.getId(),
          binaryContent.getFileName(),
          binaryContent.getSize(),
          binaryContent.getContentType(),
          storage.get(binaryContent.getId()).readAllBytes()
      );
    } catch (Exception e) {
      throw new RuntimeException("BinaryContentMapper.toDto에서 예외 발생", e);
    }
  }
}
