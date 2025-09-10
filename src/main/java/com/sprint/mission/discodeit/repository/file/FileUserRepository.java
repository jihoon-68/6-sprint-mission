package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserRepository implements UserRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","UserData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public User save(User user) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(user.getId());
        FileInitSaveLoad.<User>save(filePath, user);

        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return findAll()
                .stream()
                .filter(u->u.getId().equals(id))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return FileInitSaveLoad.<User>load(directory);
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
