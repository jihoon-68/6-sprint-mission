package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final File file;

    public FileChannelRepository(String filename) {
        this.file = new File(filename);
        if (!file.exists()) {
            saveData(new HashMap<>());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, Channel> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, Channel>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Channel 데이터 불러오기 실패", e);
        }
    }

    private void saveData(Map<UUID, Channel> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Channel 데이터 저장 실패", e);
        }
    }

    @Override
    public Channel save(Channel channel) {
        Map<UUID, Channel> data = loadData();
        data.put(channel.getId(), channel);
        saveData(data);
        return channel;
    }

    @Override
    public Channel findById(UUID id) {
        return loadData().get(id);
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(loadData().values());
    }

    @Override
    public boolean deleteById(UUID id) {
        Map<UUID, Channel> data = loadData();
        if (data.remove(id) != null) {
            saveData(data);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        saveData(new HashMap<>());
    }
}
