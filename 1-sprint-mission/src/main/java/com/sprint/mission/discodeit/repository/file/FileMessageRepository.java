package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {

    private final Path file = Paths.get("message.ser");
    private Map<UUID, Message> store = new HashMap<>();

    @SuppressWarnings("unchecked")
    private void load() {
        if (Files.exists(file)) {
            try (var in = new ObjectInputStream(Files.newInputStream(file))) {
                store = (Map<UUID, Message>) in.readObject();
            } catch (Exception ignored) {
                store = new HashMap<>();
            }
        }
    }

    private void persist() {
        try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(store);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public FileMessageRepository() {
        load();
    }

    @Override
    public Message save(Message message) {
        store.put(message.getId(), message);
        persist();
        return message;
    }

    @Override
    public Message findById(UUID id) {
        return store.get(id);
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(UUID id) {
        if (existsById(id)) {
            store.remove(id);
            return true;
        } else {
            System.out.println("실패");
            return false;
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
