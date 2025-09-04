package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    private JCFMessageService(UserService userService, ChannelService channelService) {
        this.data = new HashMap<>();
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message create(String content, UUID user, UUID channel, Message.MessageType type) {
        Message message = new Message(content, user, channel, type);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Message> readUser(User user) {
        return data.values().stream()
                .filter(message -> message.getUser().equals(user.getId()))
                .toList();
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return data.values().stream()
                .filter(message -> message.getChannel().equals(channel.getId()))
                .toList();
    }

    @Override
    public List<Message> readContent(String content) {
        return data.values().stream()
                .filter(message -> message.getContent().contains(content))
                .toList();
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean update(UUID id, String content) {
        Message message = data.get(id);
        if (message == null) { return false; }
        message.update(content);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}