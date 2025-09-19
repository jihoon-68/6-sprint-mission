package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channeldto.*;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(CreateChannel createChannel);
    Channel find(UUID channelId);
    List<Channel> findAllByUserId(UUID channelId);
    Channel update(UUID channelId, UpdateChannel updatePublicChannelDto);
    void delete(UUID channelId);
}
