package com.sprint.mission.discodeit.dto.channel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChannelResponseDto(
        UUID id,
        String name,
        String description,
        Optional<Instant> lastMessageSentAt, // null 허용
        List<UUID> participants // null 허용. PRIVATE 채널만 사용
){
}
