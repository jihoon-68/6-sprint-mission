package com.sprint.mission.discodeit.repository.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Primary
@Repository
public class FileMessageRepository implements MessageRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".json";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module());

    public FileMessageRepository(
            @Value("${discodeit.repository.file-directory:db}") String fileDirectory
    ) {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), fileDirectory, Message.class.getSimpleName());
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private Path getPath(UUID uuid) {
        return DIRECTORY.resolve(uuid.toString() + EXTENSION);
    }

    @Override
    public Message save(Message message) {
        Path path = getPath(message.getId());
        try {
            objectMapper.writeValue(path.toFile(), message);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return message;
    }

    @Override
    public Optional<Message> findById(UUID uuid) {
        Path path = getPath(uuid);
        if (Files.notExists(path)) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(path.toFile(), Message.class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<Message> findAll() {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), Message.class);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void deleteById(UUID uuid) {
        Path path = getPath(uuid);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Override
    public boolean existsById(UUID uuid) {
        return Files.exists(getPath(uuid));
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), Message.class);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .filter(message -> message.getChannelId().equals(channelId))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        findAllByChannelId(channelId).forEach(message -> deleteById(message.getId()));
    }
}
