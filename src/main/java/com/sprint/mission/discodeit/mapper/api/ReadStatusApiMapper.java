package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.response.ReadStatusResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.ReadStatusResponseDTO.FindReadStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusApiMapper {

  public FindReadStatusResponse toReadStatusResponse(ReadStatusDTO.ReadStatus readStatus) {
    return ReadStatusResponseDTO.FindReadStatusResponse.builder()
        .id(readStatus.getId())
        .channelId(readStatus.getChannelId())
        .userId(readStatus.getUserId())
        .lastReadAt(readStatus.getLastReadAt())
        .build();
  }

}
