package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {
    Channel save(Channel channel);
    Optional<Channel> readId(UUID id);
    List<Channel> readTitle(String title);
    List<Channel> readAll();
    boolean delete(UUID id);
}
