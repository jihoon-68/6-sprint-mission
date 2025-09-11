package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileChannelRepository implements ChannelRepository {
    private final List<Channel> channels;

    public  FileChannelRepository() {
        channels = loadChannels();
    }

    private List<Channel> loadChannels() {
        try (FileInputStream fis = new FileInputStream("./src/main/resources/channels.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void exploreChannels() {
        try (FileOutputStream fos = new FileOutputStream("./src/main/resources/channels.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject((Object) channels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Channel channel) {
        if (existsById(channel.getId())) {
            Channel updateChannel = findById(channel.getId());
            updateChannel.updateChannelName(channel.getChannelName());
        } else {
            channels.add(channel);
        }
        exploreChannels();
    }

    @Override
    public void deleteById(UUID id) {
            channels.removeIf(channel -> channel.getId().equals(id));
            exploreChannels();
    }

    @Override
    public List<Channel> findAll() {
        return channels;
    }

    @Override
    public Channel findById(UUID id) {
        return channels.stream().filter(channel -> channel.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Channel> findByOwnerId(UUID id) {
        return channels.stream().filter(channel -> channel.getOwnerId().equals(id)).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return channels.stream().anyMatch(channel -> channel.getId().equals(id));
    }
}
