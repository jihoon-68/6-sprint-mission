package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.File;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final SerializableStore<Channel> store;
    private final Map<UUID, Channel> data;

    public FileChannelService(String baseDir) {
        this.store = new SerializableStore<>(new File(baseDir, "channels.ser"));
        this.data = store.load();
    }

    @Override
    public Channel create(ChannelType type, String name, String description) {
        Channel c = new Channel(type, name, description);
        data.put(c.getId(), c);
        store.save(data);
        return c;
    }

    @Override
    public Optional<Channel> findById(UUID id) { return Optional.ofNullable(data.get(id)); }
    @Override
    public List<Channel> findAll() { return new ArrayList<>(data.values()); }

    @Override
    public Optional<Channel> update(UUID id, ChannelType type, String name, String description) {
        Channel c = data.get(id);
        if (c == null) return Optional.empty();
        c.update(type, name, description);
        store.save(data);
        return Optional.of(c);
    }

    @Override
    public boolean delete(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) store.save(data);
        return removed;
    }
}