package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ServiceFactory;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;
    private static JCFMessageService instance;
    private JCFMessageService() {
        this.data = new ConcurrentHashMap<>();
        this.userService = ServiceFactory.getUserService();
        this.channelService = ServiceFactory.getChannelService();
    }

    public static synchronized JCFMessageService getInstance() {
        if (instance == null) {
            instance = new JCFMessageService();
        }
        return instance;
    }

    @Override
    public Message create(String content, User user, Channel channel, Message.MessageType type) {
        if (!userService.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User not found" + user.getId());
        }
        if (!channelService.findById(channel.getId()).isPresent()) {
            throw new IllegalArgumentException("Channel not found" + channel.getId());
        }
        Message message = new Message(content, user, channel, type);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Message> update(UUID id, String content) {
        Message message = data.get(id);
        if (message == null) {
            return Optional.empty();
        }
        message.update(content);
        return Optional.of(message);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}