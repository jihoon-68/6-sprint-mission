package com.sprint.mission.discodeit.DTO.Channel;

<<<<<<< HEAD

public record CreatePublicChannelDTO(
        String name,
        String description
=======
import com.sprint.mission.discodeit.Enum.ChannelType;

public record CreatePublicChannelDTO(
        String channelName,
        String description,
        ChannelType channelType
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {

}
