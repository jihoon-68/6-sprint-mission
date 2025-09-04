package com.sprint.mission.discodeit.service.file;


import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class FileChannelService implements ChannelService{

    private final File file;

    public FileChannelService(String filename) {
        this.file = new File(filename);
        if (!file.exists()) {
            saveData(new HashMap<>());
        }
    }

    public void reset() {
        saveData(new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, Channel> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, Channel>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("채널 데이터 불러오기 실패", e);
        }
    }

    private void saveData(Map<UUID, Channel> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("채널 데이터 저장 실패", e);
        }
    }

    @Override
    public Channel create(Channel channel) {
        Map<UUID, Channel> data = loadData();
        data.put(channel.getId(), channel);
        saveData(data);
        return channel;
    }

    @Override
    public Channel read(UUID id) {
        return loadData().get(id);
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(loadData().values());
    }

    @Override
    public Channel update(UUID id, String channelName, String channelDescription) {
        Map<UUID, Channel> data = loadData();
        Channel channel = data.get(id);
        if (channel != null) {
            channel.setChannelName(channelName);
            channel.setChannelDescription(channelDescription);
            saveData(data);
        }
        return channel;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Channel> data = loadData();
        if (data.remove(id) != null) {
            saveData(data);
            return true;
        }
        return false;
    }


}
