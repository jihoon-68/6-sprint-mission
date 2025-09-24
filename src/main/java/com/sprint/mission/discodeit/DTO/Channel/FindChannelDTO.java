package com.sprint.mission.discodeit.DTO.Channel;
<<<<<<< HEAD

import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
=======
import com.sprint.mission.discodeit.Enum.ChannelType;
>>>>>>> 1f5633e (feat: API 스펙에 밎게 DTO 수정)
import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record FindChannelDTO(
        UUID id,
        ChannelType type,
        String name,
        String description,
        List<UUID> participantIds,
        Instant lastMessageAt
) {
    public FindChannelDTO(Channel channel,Instant lastMessageAt,List<UUID> participantIds) {
        this(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                participantIds,
                lastMessageAt
        );
    }

    static public FindChannelDTO createPublicChannelDto(Channel channel, Instant lastMessageAt) {
        return new FindChannelDTO(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                new ArrayList<>(),
                lastMessageAt
        );
    }
}
