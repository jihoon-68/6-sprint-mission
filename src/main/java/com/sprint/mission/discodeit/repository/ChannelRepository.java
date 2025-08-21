package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    void enroll(Channel channel);
    Channel findById(UUID userId);
    void delete(UUID userId);
    List<Channel> findAll();
}
