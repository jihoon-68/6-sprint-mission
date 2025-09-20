package com.sprint.mission.discodeit.dto.channel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChannelResponseDto(
        UUID id,
        String name,
        String description,
        Instant lastMessageSentAt, // null 허용
        List<UUID> participants // PRIVATE 채널만 사용
){
    // PUBLIC 채널용
    public static ChannelResponseDto publicChannel(UUID id, String name,  String description,
                                                   Instant lastMessageSentAt) {
        return new ChannelResponseDto(
                id,
                name,
                description,
                lastMessageSentAt,
                List.of() // null이면 빈 리스트
        );
    }

    // PRIVATE 채널용
    public static ChannelResponseDto privateChannel(UUID id, Instant lastMessageSentAt,
                                                    List<UUID> participants) {
        return new ChannelResponseDto(
                id,
                "", // private 채널은 name이 없음.
                "", // private 채널은 description이 없음.
                lastMessageSentAt,
                participants != null ? List.copyOf(participants) : List.of()
        );
    }

    // lastMessageSentAt 없는 경우
    public static ChannelResponseDto withoutLastMessage(UUID id, String name, String description, List<UUID> participants) {
        return new ChannelResponseDto(
                id,
                name,
                description,
                null,
                participants != null ? java.util.List.copyOf(participants) : java.util.List.of()
        );
    }


}