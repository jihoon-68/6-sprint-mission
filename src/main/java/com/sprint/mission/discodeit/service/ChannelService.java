package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    Channel create(String title, String description, Channel.ChannelType type);
    Optional<Channel> readId(UUID id);
    List<Channel> readTitle(String title);
    List<Channel> readAll();
    boolean update(UUID id, String title, String description);
    boolean delete(UUID id);
}
