package com.sprint.mission.discodeit.dto.Channel;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public record CreateChannelDto(
        List<User> users,
        String name,
        String description
) {
}
