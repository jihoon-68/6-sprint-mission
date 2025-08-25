package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {
    private final List<Channel> channels;

    public JCFChannelRepository() {channels = new ArrayList<>();}


    @Override
    public void save(Channel channel) {
        channels.add(channel);
    }

    @Override
    public void remove(Channel channelId) {
        channels.remove(channelId);
    }

    @Override
    public List<Channel> findAll() {
        if(channels.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(channels);
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return channels.stream().filter(c -> c.getUuid().equals(id)).findFirst();
    }

    @Override
    public Optional<Channel> findByName(String channelName) {
        return channels.stream().filter(c -> c.getChannelName().equals(channelName)).findFirst();
    }

    @Override
    public boolean existsByName(String channelName) {
        return channels.stream().anyMatch(c -> c.getChannelName().equals(channelName));
    }
}
