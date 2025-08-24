package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {

    private static final String FILE_PATH;

    static {
        try {
            URI fileUri = FileChannelRepository.class
                    .getClassLoader()
                    .getResource("")
                    .toURI()
                    .resolve("channel.ser");
            FILE_PATH = Path.of(fileUri).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Channel serialization path: " + FILE_PATH);
    }

    @Override
    public Channel save(Channel channel) {
        Map<UUID, Channel> data = readData();
        data.put(channel.id(), channel);
        writeData(data);
        return channel;
    }

    @Override
    public Optional<Channel> find(UUID id) {
        Map<UUID, Channel> data = readData();
        Channel channel = data.get(id);
        return Optional.ofNullable(channel);
    }

    @Override
    public Set<Channel> findAll() {
        Map<UUID, Channel> data = readData();
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Channel> data = readData();
        data.remove(id);
        writeData(data);
    }

    private Map<UUID, Channel> readData() {
        try (var in = new ObjectInputStream(Files.newInputStream(Path.of(FILE_PATH)))) {
            Object o = in.readObject();
            if (!(o instanceof Map<?, ?> data)) {
                throw new ClassCastException();
            }
            Map<UUID, Channel> result = new HashMap<>();
            for (Object e : data.entrySet()) {
                if (!(e instanceof Map.Entry<?, ?> entry)) {
                    throw new ClassCastException();
                }
                if (!(entry.getKey() instanceof UUID id) || !(entry.getValue() instanceof Channel channel)) {
                    throw new ClassCastException();
                }
                result.put(id, channel);
            }
            return result;
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(Map<UUID, Channel> data) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(Path.of(FILE_PATH)))) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
