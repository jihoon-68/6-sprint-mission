package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    private static JCFChannelService instance;

    private JCFChannelService() {
        this.data = new ConcurrentHashMap<>();
    }

    public static synchronized JCFChannelService getInstance() {
        if (instance == null) {
            instance = new JCFChannelService();
        }
        return instance;
    }

    @Override
    public Channel create(String title, String description, Channel.ChannelType type) {
        Channel channel = new Channel(title, description, type);
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Channel> findByTitle(String title) {
        return data.values().stream()
                .filter(channel -> channel.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Channel> update(UUID id, String title, String description) {
        Channel channel = data.get(id);
        if (channel == null) {
            return Optional.empty();
        }

        channel.update(title, description);
        return Optional.of(channel);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}