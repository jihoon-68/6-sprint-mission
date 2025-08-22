package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.Map;
import java.util.UUID;

public interface ChannelRepository {
    public boolean save(Channel channel);
    public Map<UUID, Channel> getAllChannels();
    public boolean delete(UUID channelId);
    public boolean update(Channel channel);
}
