package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel create(String title, String description, Channel.ChannelType type) {
        return channelRepository.save(new Channel(title, description, type));
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return channelRepository.readId(id);
    }

    @Override
    public List<Channel> readTitle(String title) {
        return channelRepository.readTitle(title);
    }

    @Override
    public List<Channel> readAll() {
        return channelRepository.readAll();
    }

    @Override
    public boolean update(UUID id, String title, String description) {
        Optional<Channel> channelToUpdate = channelRepository.readId(id);
        if (channelToUpdate.isEmpty()) { return false; }
        channelToUpdate.get().update(title, description);
        channelRepository.save(channelToUpdate.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return channelRepository.delete(id);
    }
}
