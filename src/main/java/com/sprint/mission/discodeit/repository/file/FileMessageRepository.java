package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    private final Path directory;

    public FileMessageRepository(String string) {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "messages");
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

    private void save(Message message) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath(message.getmessageId()).toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Message load(Path filePath) {
        try (
                FileInputStream fis = new FileInputStream(filePath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message create(Message message) {
        save(message);
        return message;
    }

    @Override
    public Message read(UUID id) {
        Path path = filePath(id);
        if (!Files.exists(path)) return null;
        return load(path);
    }

    @Override
    public List<Message> readAll() {
        try {
            return Files.list(directory)
                    .map(this::load)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message update(UUID id, Message message) {
        if (!Files.exists(filePath(id))) return null;
        save(message);
        return message;
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
