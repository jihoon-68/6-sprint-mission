package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.entity.Message;

public class MessageMapper {
    public static MessageResponseDto toDto(Message message) {
        return MessageResponseDto.builder()
                .id(message.getId())
                .authorId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .binaryContents(message.getBinaryContents())
                .build();
    }
}
