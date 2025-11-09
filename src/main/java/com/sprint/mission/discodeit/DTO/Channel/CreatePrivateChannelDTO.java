package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.Enum.ChannelType;
import java.util.List;
import java.util.UUID;

public record CreatePrivateChannelDTO(
        List<UUID> participantIds
) {}
