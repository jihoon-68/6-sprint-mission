package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository extends SaveAndLoadCommon implements MessageRepository, Serializable {
    private final Path path = Paths.get(System.getProperty("user.dir"), "messages");

    public FileMessageRepository() {init(path);}


    @Override
    public void save(Message message) {
        Path filePath = path.resolve(message.getUuid().toString().concat(".ser"));
        save(filePath, message);
    }

    @Override
    public void remove(Message message) throws NotFoundException {
        try{
            Path filePath = path.resolve(message.getUuid().toString().concat(".ser"));
            Files.deleteIfExists(filePath);
        } catch (Exception e){
        }
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = load(path);
        return messages == null ? List.of() : messages;
    }

    @Override
    public List<Message> findByReceiverId(UUID receiverId) {
        List<Message> messages = load(path);
        return messages.stream()
                .filter(message -> message.getReceiver().equals(receiverId))
                .toList();
    }


    @Override
    public Optional<Message> findById(UUID messageId) throws NotFoundException {
        List<Message> messages = load(path);
        return messages.stream()
                .filter(message -> message.getUuid().equals(messageId))
                .findFirst();
    }

    @Override
    public boolean existsById(UUID messageId) {
        List<Message> messages = load(path);
        return messages.stream()
                .anyMatch(message -> message.getUuid().equals(messageId));
    }
}
