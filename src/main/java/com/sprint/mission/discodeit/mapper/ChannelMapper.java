package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;
import java.util.List;

public class ChannelMapper {
    public static ChannelResponseDto toDto(Channel channel, List<UserResponseDto> participants, Instant lastMessageAt) {
        return ChannelResponseDto.builder()
                .id(channel.getId())
                .type(channel.getType())
                .name(channel.getName())
                .description(channel.getDescription())
                .participants(participants)
                .lastMessageAt(lastMessageAt)
                .build();
    }
}
