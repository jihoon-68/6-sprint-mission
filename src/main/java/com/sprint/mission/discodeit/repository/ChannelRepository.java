package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    void addChannel(Channel channel);
    Channel readChannel(UUID channelId);
    void deleteChannel(UUID channelId);
    void updateChannel(Channel channel);
    List<Channel> readAllChannel();
}
