package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        return messageRepository.save(Message.of(content, channelId, authorId));
    }

    @Override
    public Optional<Message> read(UUID id) {
        return messageRepository.find(id);
    }

    @Override
    public Set<Message> readAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message update(UUID id, String newContent) {
        Message message = messageRepository.find(id).orElseThrow();
        return messageRepository.save(message.updateContent(newContent));
    }

    @Override
    public void delete(UUID id) {
        messageRepository.delete(id);
    }
}
