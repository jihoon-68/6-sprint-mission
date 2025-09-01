package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository repository;

    public BasicChannelService(ChannelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Channel create(Channel channel) {
        return repository.create(channel);
    }

    @Override
    public Channel read(UUID id) {
        return repository.read(id);
    }

    @Override
    public List<Channel> readAll() {
        return repository.readAll();
    }

    @Override
    public Channel update(UUID id, Channel updatedChannel) {
        return repository.update(id, updatedChannel);
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
