package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public Channel create(ChannelType type, String name, String description) {
        Channel c = new Channel(type, name, description);
        data.put(c.getId(), c);
        return c;
    }

    @Override
    public Optional<Channel> findById(UUID id) { return Optional.ofNullable(data.get(id)); }

    @Override
    public List<Channel> findAll() { return new ArrayList<>(data.values()); }

    @Override
    public Optional<Channel> update(UUID id, ChannelType type, String name, String description) {
        Channel c = data.get(id);
        if (c == null) return Optional.empty();
        c.update(type, name, description);
        return Optional.of(c);
    }

    @Override
    public boolean delete(UUID id) { return data.remove(id) != null; }
}
