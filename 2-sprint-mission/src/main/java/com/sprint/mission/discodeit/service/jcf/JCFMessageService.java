package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final ChannelService channelService;
    private final UserService userService;
    private final Map<UUID, Message> data;

    public JCFMessageService(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
        this.data = new HashMap<>();
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        channelService.read(authorId).orElseThrow();
        userService.read(authorId).orElseThrow();
        Message newMessage = Message.of(content, channelId, authorId);
        data.put(newMessage.id(), newMessage);
        return newMessage;
    }

    @Override
    public Optional<Message> read(UUID id) {
        Message message = data.get(id);
        return Optional.ofNullable(message);
    }

    @Override
    public Set<Message> readAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public Message update(UUID id, String newContent) {
        return data.compute(id,
                (keyId, valueMessage) -> Objects.requireNonNull(valueMessage).updateContent(newContent));
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
