package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class FileChannelRepository extends SaveAndLoadCommon implements Serializable, ChannelRepository {
    private final Path path = Paths.get(System.getProperty("user.dir"), "channels");

    public FileChannelRepository() {init(path);}

    @Override
    public void save(Channel channel) {
        Path filePath = path.resolve(channel.getUuid().toString().concat(".ser"));
        save(filePath, channel);
    }

    @Override
    public void remove(Channel channelId) {
        try{
            Path filePath = path.resolve(channelId.getUuid().toString().concat(".ser"));
            Files.deleteIfExists(filePath);
        } catch (Exception e){
        }
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channels = load(path);
        return channels == null ? List.of() : channels;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        List<Channel> channels = load(path);
        return channels.stream()
                .filter(channel -> channel.getUuid().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Channel> findByName(String channelName) {
        List<Channel> channels = load(path);
        return channels.stream()
                .filter(channel -> channel.getChannelName().equals(channelName))
                .findFirst();
    }

    @Override
    public boolean existsByName(String channelName) {
        List<Channel> channels = load(path);
        return channels.stream().anyMatch(channel -> channel.getChannelName().equals(channelName));
    }


}
