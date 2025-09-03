package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository channels;

    public BasicChannelService(ChannelRepository channels) {
        this.channels = channels;
    }

    @Override
    public Channel create(String title, String description, Channel.ChannelType type) {
        return channels.save(new Channel(title, description, type));
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return channels.readId(id);
    }

    @Override
    public List<Channel> readTitle(String title) {
        return channels.readTitle(title);
    }

    @Override
    public List<Channel> readAll() {
        return channels.readAll();
    }

    @Override
    public boolean update(UUID id, String title, String description) {
        Optional<Channel> channelToUpdate = channels.readId(id);
        if (channelToUpdate.isEmpty()) return false;
        channelToUpdate.get().update(title, description);
        channels.save(channelToUpdate.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return channels.delete(id);
    }
}
