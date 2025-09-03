package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.*;
import java.util.*;

public class FileMessageService implements MessageService {
    private final File file = new File("data/message.ser");

    private Map<UUID, Message> loadAll() {
        if (!file.exists()) { return new HashMap<>(); }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void saveAll(Map<UUID, Message> data) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message create(String content, UUID user, UUID channel, Message.MessageType type) {
        Map<UUID, Message> data = loadAll();
        Message newMessage = new Message(content, user, channel, type);
        data.put(newMessage.getId(), newMessage);
        saveAll(data);
        return newMessage;
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return Optional.ofNullable(loadAll().get(id));
    }

    @Override
    public List<Message> readUser(User user) {
        return loadAll().values().stream()
                .filter(message -> message.getUser().equals(user.getId()))
                .toList();
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return loadAll().values().stream()
                .filter(message -> message.getChannel().equals(channel.getId()))
                .toList();
    }

    @Override
    public List<Message> readContent(String content) {
        return loadAll().values().stream()
                .filter(message -> message.getContent().contains(content))
                .toList();
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(loadAll().values());
    }

    @Override
    public boolean update(UUID id, String content) {
        Map<UUID, Message> data = loadAll();
        Message messageToUpdate = data.get(id);
        if (messageToUpdate == null) {
            return false;
        }
        messageToUpdate.update(content);
        data.put(id, messageToUpdate);
        saveAll(data);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Message> data = loadAll();
        boolean removed = data.remove(id) != null;
        if (removed) { saveAll(data); }
        return removed;
    }
}