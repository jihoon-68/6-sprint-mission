package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    private static JCFChannelService instance;

    private JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public Channel create(String title, String description, Channel.ChannelType type) {
        Channel channel = new Channel(title, description, type);
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Channel> readTitle(String title) {
        return data.values().stream()
                .filter(channel -> channel.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean update(UUID id, String title, String description) {
        Channel channel = data.get(id);
        if (channel == null) {
            return false;
        }
        channel.update(title, description);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}