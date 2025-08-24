package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
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

public class FileUserRepository implements UserRepository {

    private static final String FILE_PATH;

    static {
        try {
            URI fileUri = FileUserRepository.class
                    .getClassLoader()
                    .getResource("")
                    .toURI()
                    .resolve("user.ser");
            FILE_PATH = Path.of(fileUri).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User serialization path: " + FILE_PATH);
    }

    @Override
    public User save(User user) {
        Map<UUID, User> data = readData();
        data.put(user.id(), user);
        writeData(data);
        return user;
    }

    @Override
    public Optional<User> find(UUID id) {
        Map<UUID, User> data = readData();
        User user = data.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Set<User> findAll() {
        Map<UUID, User> data = readData();
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, User> data = readData();
        data.remove(id);
        writeData(data);
    }

    private Map<UUID, User> readData() {
        try (var in = new ObjectInputStream(Files.newInputStream(Path.of(FILE_PATH)))) {
            Object o = in.readObject();
            if (!(o instanceof Map<?, ?> data)) {
                throw new ClassCastException();
            }
            Map<UUID, User> result = new HashMap<>();
            for (Object e : data.entrySet()) {
                if (!(e instanceof Map.Entry<?, ?> entry)) {
                    throw new ClassCastException();
                }
                if (!(entry.getKey() instanceof UUID id) || !(entry.getValue() instanceof User user)) {
                    throw new ClassCastException();
                }
                result.put(id, user);
            }
            return result;
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(Map<UUID, User> data) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(Path.of(FILE_PATH)))) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
