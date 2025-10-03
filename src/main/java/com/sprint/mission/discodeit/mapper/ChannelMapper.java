package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.entity.Channel;

public class ChannelMapper {
    public static ChannelResponseDto toDto(Channel channel) {
        return ChannelResponseDto.builder()
                .id(channel.getId())
                .type(channel.getType())
                .name(channel.getName())
                .description(channel.getDescription())
                .build();
    }
}
