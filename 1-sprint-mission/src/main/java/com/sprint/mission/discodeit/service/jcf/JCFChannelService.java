package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final List<Channel> data = new ArrayList<>();

    @Override
    public Channel create(String name, List<User> users, List<Message> messages) {
        Channel channel = new Channel(name, users, messages);
        data.add(channel);
        return channel;
    }

    @Override
    public Channel findById(UUID id) {
        return data.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Channel> findAll() {
        return data;
    }

    @Override
    public Channel update(UUID id, String name, String topic) {
        Channel ch = findById(id);
        if (ch == null) throw new NoSuchElementException("Channel not found: " + id);
        ch.update(name);
        return ch;
    }

    @Override
    public boolean delete(UUID id) {
        Channel ch = findById(id);
        if (ch == null) throw new NoSuchElementException("Channel not found: " + id);

        return data.remove(ch);
    }
}