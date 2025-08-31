package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private final Path directory;

    public FileUserRepository(String string) {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "users");
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

    private void save(User user) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath(user.getuserId()).toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User load(Path filePath) {
        try (
                FileInputStream fis = new FileInputStream(filePath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User create(User user) {
        save(user);
        return user;
    }

    @Override
    public User read(UUID id) {
        Path path = filePath(id);
        if (!Files.exists(path)) return null;
        return load(path);
    }

    @Override
    public List<User> readAll() {
        try {
            return Files.list(directory)
                    .map(this::load)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(UUID id, User user) {
        if (!Files.exists(filePath(id))) return null;
        save(user);
        return user;
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
