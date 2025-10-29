package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusMapper {

  public ReadStatusDto toDto(ReadStatus entity) {
    if (entity == null) {
      return null;
    }

    return new ReadStatusDto(
        entity.getPkUser().getId(),
        entity.getPkChannel().getId(),
        entity.getLastReadAt()
    );
  }
}