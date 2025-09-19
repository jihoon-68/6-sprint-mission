package com.sprint.mission.discodeit.dto.channeldto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.UUID;

public record UpdateChannel(
        ChannelType type,       // public만 가능
        UUID channelId,
        String newName,
        String newDescription
) {}
