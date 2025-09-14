package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserStatusRepository implements UserStatusRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileUserStatusRepository() {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), "file-data-map", UserStatus.class.getSimpleName());
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        Path path = resolvePath(userStatus.getId());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(userStatus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        Path path = resolvePath(id);
        if (Files.notExists(path)) return Optional.empty();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return Optional.of((UserStatus) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(us -> us.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        try {
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> findById(UUID.fromString(path.getFileName().toString().replace(EXTENSION, ""))).orElse(null))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return Files.exists(resolvePath(id));
    }

    @Override
    public void deleteById(UUID id) {
        try {
            Files.deleteIfExists(resolvePath(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
