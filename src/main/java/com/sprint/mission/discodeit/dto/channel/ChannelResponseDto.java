package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChannelResponseDto(
        UUID id,
        ChannelType type,
        String name,
        String description
        // List<UUID> participantIds, // PRIVATE 채널만 사용
        // Instant lastMessageAt // null 허용
){
    // PUBLIC 채널용
    public static ChannelResponseDto publicChannel(UUID id, String name, String description,
                                                   Instant lastMessageAt) {
        return new ChannelResponseDto(
                id,
                ChannelType.PUBLIC,
                name,
                description
                // List.of(), // null이면 빈 리스트
                // lastMessageAt
        );
    }

    // PRIVATE 채널용
    public static ChannelResponseDto privateChannel(UUID id, Instant lastMessageAt,
                                                    List<UUID> participantIds) {
        return new ChannelResponseDto(
                id,
                ChannelType.PRIVATE,
                "", // private 채널은 name이 없음.
                "" // private 채널은 description이 없음.
                // participantIds != null ? List.copyOf(participantIds) : List.of(),
                // lastMessageAt
        );
    }

//    // lastMessageSentAt 없는 경우
//    public static ChannelResponseDto withoutLastMessage(UUID id, String name, String description, List<UUID> participantsIds) {
//        return new ChannelResponseDto(
//                id,
//                name,
//                description,
//                null,
//                participantIds != null ? java.util.List.copyOf(participantsIds) : java.util.List.of()
//        );
//    }


}