package com.sprint.mission.discodeit.DTO.Channel;

<<<<<<< HEAD
=======
import com.sprint.mission.discodeit.Enum.ChannelType;

>>>>>>> 7c7532b (박지훈 sprint3 (#2))
import java.util.List;
import java.util.UUID;

public record CreatePrivateChannelDTO(
<<<<<<< HEAD
        List<UUID> participantIds
=======
        List<UUID> userIds,
        ChannelType channelType
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {}
