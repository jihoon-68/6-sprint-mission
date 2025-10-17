package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinaryContentMapper {

  public BinaryContentDto toDto(BinaryContent binaryContent) {
    return new BinaryContentDto(
        binaryContent.getId(),
        binaryContent.getFileName(),
        binaryContent.getSize(),
        binaryContent.getContentType()
    );
  }

  public List<BinaryContentDto> toDtoList(List<BinaryContent> binaryContentList) {
    return binaryContentList.stream()
        .map(this::toDto)
        .toList();
  }
}
