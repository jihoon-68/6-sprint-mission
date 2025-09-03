package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.*;
import java.util.UUID;

public interface ChannelService {
    Channel create(ChannelType type, String name, String description);
    Optional<Channel> findById(UUID id);
    List<Channel> findAll();
    Optional<Channel> update(UUID id, ChannelType type, String name, String description);
    boolean delete(UUID id);
}