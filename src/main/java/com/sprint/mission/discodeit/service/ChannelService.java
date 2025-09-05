package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(String channelName, String text, ChannelType type);
    Channel find(UUID id);
    List<Channel> findAll();
    void update(Channel channel);
    void delete(UUID id);
}
