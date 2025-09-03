package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFMessageService implements MessageService {
    private final List<Message> data = new ArrayList<>();
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = Objects.requireNonNull(userService, "userService");
        this.channelService = Objects.requireNonNull(channelService, "channelService");
    }

    @Override
    public Message create(User author, String content, Channel channel) {
        System.out.println("[create] author=" + author);
        Message message = new Message(author, content, channel);

        data.add(message);
        return message;
    }

    @Override
    public Message findById(UUID id) {
        System.out.println("[findById] id=" + id);

        return data.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Message> findAll() {
        return data;
    }

    @Override
    public Message update(UUID id, String content) {
        Message message = findById(id);

        if (message == null) throw new NoSuchElementException("Message not found: " + id);

        message.update(content);

        return message;
    }

    @Override
    public boolean delete(UUID id) {
        Message message = findById(id);
        if (message == null) throw new NoSuchElementException("Message not found: " + id);

        return data.remove(message);
    }
}