package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {

    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        data = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        data.put(channel.id(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> find(UUID id) {
        Channel channel = data.get(id);
        return Optional.ofNullable(channel);
    }

    @Override
    public Set<Channel> findAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
