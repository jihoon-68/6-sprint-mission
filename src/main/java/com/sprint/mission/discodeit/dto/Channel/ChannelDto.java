package com.sprint.mission.discodeit.dto.Channel;

import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.enumtype.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelDto (
        UUID id,
        ChannelType type,
        String name,
        String description,
        List<UserDto> participants,
        Instant lastMessageAt
){
}
