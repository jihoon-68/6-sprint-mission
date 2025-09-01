package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository repository;

    public BasicMessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message create(Message message) {
        return repository.create(message);
    }

    @Override
    public Message read(UUID id) {
        return repository.read(id);
    }

    @Override
    public List<Message> readAll() {
        return repository.readAll();
    }

    @Override
    public Message update(UUID id, Message updatedMessage) {
        return repository.update(id, updatedMessage);
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
