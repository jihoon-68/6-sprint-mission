package com.sprint.mission.discodeit.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
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
public class FileReadStatusRepository implements ReadStatusRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".json";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module());

    public FileReadStatusRepository(
            @Value("${discodeit.repository.file-directory:db}") String fileDirectory
    ) {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), fileDirectory, ReadStatus.class.getSimpleName());
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
    public ReadStatus save(ReadStatus readStatus) {
        Path path = getPath(readStatus.getId());
        try {
            objectMapper.writeValue(path.toFile(), readStatus);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID uuid) {
        Path path = getPath(uuid);
        if (Files.notExists(path)) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(path.toFile(), ReadStatus.class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<ReadStatus> findAll() {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), ReadStatus.class);
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
    public List<ReadStatus> findAllByUserId(UUID userId) {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), ReadStatus.class);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .filter(readStatus -> readStatus.getUserId().equals(userId))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<ReadStatus> findAllByChannelId(UUID channelId) {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), ReadStatus.class);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        findAllByChannelId(channelId).forEach(readStatus -> deleteById(readStatus.getId()));
    }
}
