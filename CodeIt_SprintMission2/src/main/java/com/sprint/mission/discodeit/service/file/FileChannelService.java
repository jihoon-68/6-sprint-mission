package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.awt.desktop.PreferencesEvent;
import java.io.*;
import java.util.*;

public class FileChannelService implements Serializable, ChannelService {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "channels.ser";
    private Map<UUID, Channel> data = new HashMap<UUID, Channel>();

    public FileChannelService() {
        loadFromFile();
    }

    @Override
    public Channel createChannel(String name, String description) {
        Channel channel = new Channel(name, description);
        data.put(UUID.randomUUID(), channel);
        saveToFile();
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<Channel>(data.values());
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
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            oos.writeObject(data);
        } catch (IOException ex) {
            System.err.println("Error saving file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            data = (Map<UUID, Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading channels from file" + e.getMessage());
            data = new HashMap<>();
        }


    }
}
