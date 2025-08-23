package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    private final List<Message> messages;

    public JCFMessageRepository() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void save(Message message) {
        if (existsById(message.getId())) {
            Message updateMessage = findById(message.getId());
            updateMessage.updateContent(message.getContent());
        } else {
            messages.add(message);
        }
    }

    @Override
    public void deleteById(UUID id) {
        messages.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public Message findById(UUID id) {
        return messages.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Message> findByAuthorIdAndChannelId(UUID authorId, UUID channelId) {
        return messages.stream().filter(m -> m.getAuthorId().equals(authorId) && m.getChannelId().equals(channelId)).toList();
    }

    @Override
    public List<Message> findAll() {
        return messages;
    }

    @Override
    public List<Message> findByChannelId(UUID id) {
        return messages.stream().filter(m -> m.getChannelId().equals(id)).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return messages.stream().anyMatch(m -> m.getId().equals(id));
    }
}
