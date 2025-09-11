package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileMessageRepository implements MessageRepository {
    private final List<Message> messages;

    public FileMessageRepository() {
        this.messages = loadMessages();
    }

    private List<Message> loadMessages() {
        try (FileInputStream fis = new FileInputStream("./src/main/resources/messages.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return  new ArrayList<>();
        }
    }

    private void exploreMessages() {
        try (FileOutputStream fos = new FileOutputStream("./src/main/resources/messages.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Message message) {
        if (existsById(message.getId())) {
            Message  updateMessage = findById(message.getId());
            updateMessage.updateContent( message.getContent() );
        } else {
            messages.add(message);
        }
        exploreMessages();
    }

    @Override
    public void deleteById(UUID id) {
        messages.removeIf(message -> message.getId().equals(id));
        exploreMessages();
    }

    @Override
    public void deleteByChannelId(UUID id) {
        messages.removeIf(message -> Objects.equals(message.getChannelId(), id));
        exploreMessages();
    }

    @Override
    public Message findById(UUID id) {
        return messages.stream().filter(message -> message.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Message> findByAuthorIdAndChannelId(UUID authorId, UUID channelId) {
        return messages.stream().filter(message ->Objects.equals(message.getAuthorId(), authorId) && Objects.equals(message.getChannelId(), channelId))
                .toList();
    }

    @Override
    public List<Message> findAll() {
        return  messages;
    }

    @Override
    public List<Message> findByChannelId(UUID id) {
        return messages.stream().filter(message -> Objects.equals(message.getChannelId(), id)).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return messages.stream().anyMatch(message -> message.getId().equals(id));
    }
}
