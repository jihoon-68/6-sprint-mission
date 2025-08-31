package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private final Path directory;

    public FileChannelRepository(String string) {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "channels");
        init();
    }

    private void init() {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path filePath(UUID id) {
        return directory.resolve(id.toString().concat(".ser"));
    }

    private void save(Channel channel) {
        try (
                var fos = new FileOutputStream(filePath(channel.getChannelId()).toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Channel load(Path filePath) {
        try (
                FileInputStream fis = new FileInputStream(filePath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Channel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel create(Channel channel) {
        save(channel);
        return channel;
    }

    @Override
    public Channel read(UUID id) {
        Path path = filePath(id);
        if (!Files.exists(path)) return null;
        return load(path);
    }

    @Override
    public List<Channel> readAll() {
        try {
            return Files.list(directory)
                    .map(this::load)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel update(UUID id, Channel channel) {
        if (!Files.exists(filePath(id))) return null;
        save(channel);
        return channel;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            return Files.deleteIfExists(filePath(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
