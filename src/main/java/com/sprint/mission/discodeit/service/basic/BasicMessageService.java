package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messages;
    private final ChannelRepository channels;
    private final UserRepository users;

    public BasicMessageService(MessageRepository messages, ChannelRepository channels, UserRepository users) {
        this.messages = messages;
        this.channels = channels;
        this.users = users;
    }


    @Override
    public Message create(String content, UUID user, UUID channel, Message.MessageType type) {
        if (channels.readId(channel).isEmpty()) { throw new IllegalArgumentException("채널을 찾을 수 없음: " + channel); }
        if (users.readId(user).isEmpty()) { throw new IllegalArgumentException("유저를 찾을 수 없음: " + user); }
        return messages.save(new Message(content, user, channel, type));
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return messages.readId(id);
    }

    @Override
    public List<Message> readUser(User user) {
        return messages.readUser(user);
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return messages.readChannel(channel);
    }

    @Override
    public List<Message> readContent(String content) {
        return messages.readContent(content);
    }

    @Override
    public List<Message> readAll() {
        return messages.readAll();
    }

    @Override
    public boolean update(UUID id, String content) {
        Optional<Message> messageToUpdate = messages.readId(id);
        if (messageToUpdate.isEmpty()) return false;
        messageToUpdate.get().update(content);
        messages.save(messageToUpdate.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return messages.delete(id);
    }
}
