package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.sprint.mission.discodeit.entity.Channel.ChannelType;

public interface ChannelService {

    Channel create(ChannelType channelType, String channelName, String description);

    Optional<Channel> read(UUID id);

    Set<Channel> readAll();

    Channel setPublicChannel(UUID id);

    Channel updateChannelName(UUID id, String newChannelName);

    Channel updateDescription(UUID id, String newDescription);

    void delete(UUID id);
}
