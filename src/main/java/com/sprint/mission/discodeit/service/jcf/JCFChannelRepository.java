package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel create(Channel channel) {
        data.put(channel.getChannelId(), channel);
        return channel;
    }

    @Override
    public Channel read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel update(UUID id, Channel channel) {
        if (data.containsKey(id)) {
            data.put(id, channel);
            return channel;
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}
