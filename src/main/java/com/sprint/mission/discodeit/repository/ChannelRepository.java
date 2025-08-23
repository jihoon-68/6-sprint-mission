package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    void save(Channel channel);

    List<Channel> findAll();

    void deleteById(UUID id);

    Channel findById(UUID id);

    List<Channel> findByOwnerId(UUID id);

    boolean existsById(UUID id);
}
