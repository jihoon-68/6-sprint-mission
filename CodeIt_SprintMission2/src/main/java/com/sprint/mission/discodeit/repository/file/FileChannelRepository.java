package com.sprint.mission.discodeit.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class FileChannelRepository implements ChannelRepository {

    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "channels.ser";
    private Map<UUID, Channel> data = new HashMap<>();

    public FileChannelRepository() {
        loadFromFile();
    }


    @Override
    public Channel createChannel(String name, String description) {
        Channel channel = new Channel(name, description);
        data.put(channel.getId(), channel);
        saveToFile();
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel updateChannel(UUID id, String newName, String newDescription) {
        Channel channel = data.get(id);
        if (channel != null) {
            channel.updateChannel(newName, newDescription);
            saveToFile();
        }
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving channels to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            data = (Map<UUID, Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading channels from file: " + e.getMessage());
            data = new HashMap<>();
        }
    }

}
