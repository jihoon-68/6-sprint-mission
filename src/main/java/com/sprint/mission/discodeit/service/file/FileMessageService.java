package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.util.*;

public class FileMessageService implements MessageService {
    private final SerializableStore<Message> store;
    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    public FileMessageService(String baseDir, UserService userService, ChannelService channelService) {
        this.store = new SerializableStore<>(new File(baseDir, "messages.ser"));
        this.data = store.load();
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        channelService.findById(channelId).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        userService.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Message m = new Message(content, channelId, authorId);
        data.put(m.getId(), m);
        store.save(data);
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
        if (channelId != null) channelService.findById(channelId).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        if (authorId != null) userService.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        m.update(content != null ? content : m.getContent(),
                channelId != null ? channelId : m.getChannelId(),
                authorId != null ? authorId : m.getAuthorId());
        store.save(data);
        return Optional.of(m);
    }

    @Override
    public boolean delete(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) store.save(data);
        return removed;
    }
}