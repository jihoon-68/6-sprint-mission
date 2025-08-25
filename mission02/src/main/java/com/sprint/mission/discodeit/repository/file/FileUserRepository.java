package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository extends SaveAndLoadCommon implements Serializable, UserRepository {
    private final Path path = Paths.get(System.getProperty("user.dir"), "users");

    public FileUserRepository() {init(path);}

    @Override
    public void save(User user) {
        Path filePath = path.resolve(user.getUuid().toString().concat(".ser"));
        save(filePath, user);
    }

    @Override
    public void remove(User user) throws NotFoundException {
        try{
            Path filePath = path.resolve(user.getUuid().toString().concat(".ser"));
            Files.deleteIfExists(filePath);
        } catch (Exception e){
        }
    }

    @Override
    public Optional<User> findByName(String userName) throws NotFoundException {
        List<User> users = load(path);
        return users.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst();
    }

    @Override
    public Optional<User> findById(UUID userId) throws NotFoundException {
        List<User> users = load(path);
        return users.stream()
                .filter(user -> user.getUuid().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        List<User> users = load(path);
        return users == null ? List.of() : users;
    }

    @Override
    public boolean existsByName(String userName) {
        List<User> users = load(path);
        return users.stream()
                .anyMatch(user -> user.getUserName().equals(userName));
    }
}
