package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FileChannelService implements ChannelService {

    private ChannelRepository repository;

    public FileChannelService(ChannelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Channel create(String name, List<User> users, List<Message> messages) {
        var channel = new Channel(name, users, messages);
        repository.save(channel);
        return channel;
    }

    @Override
    public Channel findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Channel> findAll() {
        return repository.findAll();
    }

    @Override
    public Channel update(UUID id, String name, String topic) {
        var channel = findById(id);
        if (channel != null) return null;

        channel.update(name);

        return repository.save(channel);
    }

    @Override
    public boolean delete(UUID id) {
        return repository.deleteById(id);
    }
}
