package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final File file = new File("data/channel.ser");

    private Map<UUID, Channel> loadAll() {
        if (!file.exists()) { return new HashMap<>(); }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void saveAll(Map<UUID, Channel> data) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel create(String title, String description, Channel.ChannelType type) {
        Map<UUID, Channel> data = loadAll();
        Channel newChannel = new Channel(title, description, type);
        data.put(newChannel.getId(), newChannel);
        saveAll(data);
        return newChannel;
    }

    @Override
    public Optional<Channel> readId(UUID id) {
        return Optional.ofNullable(loadAll().get(id));
    }

    @Override
    public List<Channel> readTitle(String title) {
        return loadAll().values().stream()
                .filter(channel -> channel.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(loadAll().values());
    }

    @Override
    public boolean update(UUID id, String title, String description) {
        Map<UUID, Channel> data = loadAll();
        Channel channelToUpdate = data.get(id);
        if (channelToUpdate == null) { return false; }
        channelToUpdate.update(title, description);
        data.put(id, channelToUpdate);
        saveAll(data);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Channel> data = loadAll();
        boolean removed = data.remove(id) != null;
        if (removed) { saveAll(data); }
        return removed;
    }
}