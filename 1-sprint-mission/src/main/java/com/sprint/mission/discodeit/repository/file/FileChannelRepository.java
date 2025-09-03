package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {

    private Map<UUID, Channel> store = new HashMap<>();
    private final Path file = Paths.get("channel.ser");

    public FileChannelRepository() {
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (Files.exists(file)) {
            try (var in = new ObjectInputStream(Files.newInputStream(file))) {
                store = (Map<UUID, Channel>) in.readObject();
            } catch (Exception ignored) {
                store = new HashMap<>();
            }
        }
    }

    private void persist() {
        try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(store);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Channel save(Channel channel) {
        store.put(channel.getId(), channel);
        persist();
        return channel;
    }

    @Override
    public Channel findById(UUID id) {
        return store.get(id);
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(UUID id) {
        if (existsById(id)) {
            store.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
