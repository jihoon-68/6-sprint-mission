package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileChannelRepository implements ChannelRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","ChannelData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public Channel save(Channel channel) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(channel.getId());
        FileInitSaveLoad.<Channel>save(filePath, channel);

        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return findAll()
                .stream()
                .filter(channel -> channel.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Channel> findAll() {
        return FileInitSaveLoad.<Channel>load(directory);
    }

    @Override
    public boolean existsById(UUID id) {
        Path path = resolvePath(id);
        return Files.exists(path);
    }

    @Override
    public void deleteById(UUID id) {
        Path path = resolvePath(id);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
