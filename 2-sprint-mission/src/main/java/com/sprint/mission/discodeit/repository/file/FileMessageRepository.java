package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class FileMessageRepository implements MessageRepository {

    private static final String FILE_PATH;

    static {
        try {
            URI fileUri = FileMessageRepository.class
                    .getClassLoader()
                    .getResource("")
                    .toURI()
                    .resolve("message.ser");
            FILE_PATH = Path.of(fileUri).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message serialization path: " + FILE_PATH);
    }

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public FileMessageRepository(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message save(Message message) {
        channelRepository.find(message.channelId()).orElseThrow();
        userRepository.find(message.authorId()).orElseThrow();
        Map<UUID, Message> data = readData();
        data.put(message.id(), message);
        writeData(data);
        return message;
    }

    @Override
    public Optional<Message> find(UUID id) {
        Map<UUID, Message> data = readData();
        Message message = data.get(id);
        return Optional.ofNullable(message);
    }

    @Override
    public Set<Message> findAll() {
        Map<UUID, Message> data = readData();
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Message> data = readData();
        data.remove(id);
        writeData(data);
    }

    private Map<UUID, Message> readData() {
        try (var in = new ObjectInputStream(Files.newInputStream(Path.of(FILE_PATH)))) {
            Object o = in.readObject();
            if (!(o instanceof Map<?, ?> data)) {
                throw new ClassCastException();
            }
            Map<UUID, Message> result = new HashMap<>();
            for (Object e : data.entrySet()) {
                if (!(e instanceof Map.Entry<?, ?> entry)) {
                    throw new ClassCastException();
                }
                if (!(entry.getKey() instanceof UUID id) || !(entry.getValue() instanceof Message message)) {
                    throw new ClassCastException();
                }
                result.put(id, message);
            }
            return result;
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(Map<UUID, Message> data) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(Path.of(FILE_PATH)))) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
