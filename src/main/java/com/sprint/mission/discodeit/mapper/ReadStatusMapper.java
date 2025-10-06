package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

public class ReadStatusMapper {

    public ReadStatusDto toDto(ReadStatus readStatus){
        return new ReadStatusDto(
                readStatus.getId(),
                readStatus.getUser().getId(),
                readStatus.getChannel().getId(),
                readStatus.getLastReadAt()
        );
    }
}
