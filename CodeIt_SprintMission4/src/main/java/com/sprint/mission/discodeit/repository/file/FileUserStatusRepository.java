package com.sprint.mission.discodeit.repository.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
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
public class FileUserStatusRepository implements UserStatusRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".json";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module());

    public FileUserStatusRepository(
            @Value("${discodeit.repository.file-directory:db}") String fileDirectory
    ) {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), fileDirectory, UserStatus.class.getSimpleName());
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
    public UserStatus save(UserStatus userStatus) {
        Path path = getPath(userStatus.getId());
        try {
            objectMapper.writeValue(path.toFile(), userStatus);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID uuid) {
        Path path = getPath(uuid);
        if (Files.notExists(path)) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(path.toFile(), UserStatus.class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<UserStatus> findAll() {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), UserStatus.class);
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
    public Optional<UserStatus> findByUserId(UUID userId) {
        try (Stream<Path> stream = Files.list(DIRECTORY)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), UserStatus.class);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .filter(userStatus -> userStatus.getUserId().equals(userId))
                    .findFirst();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void deleteByUserId(UUID userId) {
        findByUserId(userId).ifPresent(userStatus -> deleteById(userStatus.getId()));
    }
}
