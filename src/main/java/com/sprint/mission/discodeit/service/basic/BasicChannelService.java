package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository repo;

    public BasicChannelService(ChannelRepository repo) { this.repo = repo; }

    @Override public Channel create(ChannelType type, String name, String description) { return repo.save(new Channel(type, name, description)); }
    @Override public Optional<Channel> findById(UUID id) { return repo.findById(id); }
    @Override public List<Channel> findAll() { return repo.findAll(); }
    @Override public Optional<Channel> update(UUID id, ChannelType type, String name, String description) {
        Optional<Channel> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Channel c = opt.get();
        c.update(type, name, description);
        repo.save(c);
        return Optional.of(c);
    }
    @Override public boolean delete(UUID id) { return repo.deleteById(id); }
}