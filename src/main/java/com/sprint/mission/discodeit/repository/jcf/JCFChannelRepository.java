package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {
    private final List<Channel> channels;

    public JCFChannelRepository() {
        this.channels = new ArrayList<>();
    }

    @Override
    public void save(Channel channel) {
        if (existsById(channel.getId())) {
            Channel updateChannel = findById(channel.getId());
            updateChannel.updateChannelName(channel.getChannelName());
        } else {
            channels.add(channel);
        }
    }

    @Override
    public List<Channel> findAll() {
        return channels;
    }

    @Override
    public void deleteById(UUID id) {
        channels.removeIf(channel -> channel.getId().equals(id));
    }

    @Override
    public Channel findById(UUID id) {
        return channels.stream().filter(channel -> channel.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Channel> findByOwnerId(UUID id) {
        return channels.stream().filter( c -> c.getOwnerId().equals(id) ).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return channels.stream().anyMatch( c -> c.getId().equals(id));
    }
}
