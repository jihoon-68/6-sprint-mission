package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

public class ReadStatusMapper {

    public static ReadStatusResponseDto toDto(ReadStatus readStatus){
        return ReadStatusResponseDto.builder()
                .id(readStatus.getId())
                .userId(readStatus.getUserId())
                .channelId(readStatus.getChannelId())
                .build();
    }

}
