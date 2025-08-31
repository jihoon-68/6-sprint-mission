package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> channels = new HashMap<>();

    @Override
    public Channel save(Channel channel) {
        channels.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return Optional.ofNullable(channels.get(id));
    }

    @Override
    public List<Channel> readTitle(String title) {
        return channels.values().stream()
                .filter(channel -> channel.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(channels.values());
    }

    @Override
    public boolean delete(UUID id) {
        return channels.remove(id) != null;
    }
}
