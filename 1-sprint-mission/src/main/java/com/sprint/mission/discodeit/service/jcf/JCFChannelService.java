package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;
        import java.util.concurrent.ConcurrentHashMap;

public class JCFChannelService implements ChannelService {
    private final Map<String, Channel> data = new HashMap<>();

    @Override
    public Channel create(Channel channel) {
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Channel findById(String id) {
        return data.get(id);
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel update(String id, String name, String topic) {
        Channel ch = data.get(id);
        if (ch == null) throw new NoSuchElementException("Channel not found: " + id);
        ch.update(name);
        return ch;
    }

    @Override
    public boolean delete(String id) {
        return data.remove(id) != null;
    }
}