package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.Map;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    @Override
    public boolean save(Channel channel) {
        return false;
    }

    @Override
    public Map<UUID, Channel> getAllChannels() {
        return Map.of();
    }

    @Override
    public boolean delete(UUID channelId) {
        return false;
    }

    @Override
    public boolean update(Channel channel) {
        return false;
    }
}
