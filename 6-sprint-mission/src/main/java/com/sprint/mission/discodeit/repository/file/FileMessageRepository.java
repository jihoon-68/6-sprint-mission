package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileMessageRepository implements MessageRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","MessageData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public Message save(Message message) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(message.getId());
        FileInitSaveLoad.<Message>save(filePath, message);

        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return findAll()
                .stream()
                .filter(msg->msg.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Message> findAll() {
        return FileInitSaveLoad.<Message>load(directory);
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
