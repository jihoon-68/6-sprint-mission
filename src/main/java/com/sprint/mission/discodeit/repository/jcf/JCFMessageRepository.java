package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> messages = new HashMap<>();

    @Override
    public Message save(Message message) {
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return Optional.ofNullable(messages.get(id));
    }

    @Override
    public List<Message> readContent(String content) {
        return messages.values().stream()
                .filter(msg -> msg.getContent().contains(content))
                .toList();
    }

    @Override
    public List<Message> readUser(User user) {
        return messages.values().stream()
                .filter(msg -> msg.getUser().equals(user))
                .toList();
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return messages.values().stream()
                .filter(msg -> msg.getChannel().equals(channel))
                .toList();
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public boolean delete(UUID id) {
        return messages.remove(id) != null;
    }
}
