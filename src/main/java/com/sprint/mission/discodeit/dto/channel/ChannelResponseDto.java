package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.ChannelType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record ChannelResponseDto(
        UUID id,
        ChannelType type,
        String name,
        String description,
        List<UserResponseDto> participants, // PRIVATE 채널만 사용
        Instant lastMessageAt // null 허용
){
    // PUBLIC 채널용
    public static ChannelResponseDto publicChannel(UUID id, String name, String description, Instant lastMessageAt) {
        return new ChannelResponseDto(
                id,
                ChannelType.PUBLIC,
                name,
                description,
                List.of(),
                lastMessageAt
        );
    }

    // PRIVATE 채널용
    public static ChannelResponseDto privateChannel(UUID id, List<UserResponseDto> participants, Instant lastMessageAt) {
        return new ChannelResponseDto(
                id,
                ChannelType.PRIVATE,
                "", // private 채널은 name이 없음.
                "", // private 채널은 description이 없음.
                participants,
                lastMessageAt
        );
    }
}