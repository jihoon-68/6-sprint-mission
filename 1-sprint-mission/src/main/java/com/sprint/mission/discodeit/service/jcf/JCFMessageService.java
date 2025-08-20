package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFMessageService implements MessageService {
    private final Map<String, Message> data = new HashMap<>();
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = Objects.requireNonNull(userService, "userService");
        this.channelService = Objects.requireNonNull(channelService, "channelService");
    }

    @Override
    public Message create(Message message) {
//        userService.findById(message.getAuthor().getId())
//                .orElseThrow(() -> new NoSuchElementException("User not found: " + message.getAuthor().getId()));
//        channelService.findById(message.getChannel().getId())
//                .orElseThrow(() -> new NoSuchElementException("Channel not found: " + message.getChannel().getId()));

        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Message findById(String id) {
        System.out.println("[findById] id=" + id);
        return data.get(id); // 없으면 null
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Message> findByChannelId(String channelId) {
        System.out.println(channelId);

        return data.values().stream()
                .filter(m -> m.getChannel() != null
                        && Objects.equals(m.getChannel().getId(), channelId))
                .collect(Collectors.toList());
    }

    @Override
    public Message update(String id, String content) {
        Message m = data.get(id);
//        if (m == null) throw new NoSuchElementException("Message not found: " + id);

        m.update(content);
        return m;
    }

    @Override
    public boolean delete(String id) {
        return data.remove(id) != null;
    }
}