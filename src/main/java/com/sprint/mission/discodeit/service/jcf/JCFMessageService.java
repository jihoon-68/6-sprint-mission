package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;
    private final UserService userService;      // for validation (심화 요구사항)
    private final ChannelService channelService; // for validation

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.data = new ConcurrentHashMap<>();
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        // Validate relations
        channelService.findById(channelId).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        userService.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Message m = new Message(content, channelId, authorId);
        data.put(m.getId(), m);
        return m;
    }

    @Override
    public Optional<Message> findById(UUID id) { return Optional.ofNullable(data.get(id)); }

    @Override
    public List<Message> findAll() { return new ArrayList<>(data.values()); }

    @Override
    public Optional<Message> update(UUID id, String content, UUID channelId, UUID authorId) {
        Message m = data.get(id);
        if (m == null) return Optional.empty();
        // Validate if changing relations
        if (channelId != null) channelService.findById(channelId).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        if (authorId != null) userService.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        m.update(content != null ? content : m.getContent(),
                channelId != null ? channelId : m.getChannelId(),
                authorId != null ? authorId : m.getAuthorId());
        return Optional.of(m);
    }

    @Override
    public boolean delete(UUID id) { return data.remove(id) != null; }
}