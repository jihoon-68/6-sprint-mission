package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public record CreateChannelRequest(
        List<User> users,
        String name,
        String description
) {
}
