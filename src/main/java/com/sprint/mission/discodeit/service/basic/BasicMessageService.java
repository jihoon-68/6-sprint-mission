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
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public BasicMessageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Message create(String content, UUID user, UUID channel, Message.MessageType type) {
        if (channelRepository.readId(channel).isEmpty()) { throw new IllegalArgumentException("채널을 찾을 수 없음: " + channel); }
        if (userRepository.readId(user).isEmpty()) { throw new IllegalArgumentException("유저를 찾을 수 없음: " + user); }
        return messageRepository.save(new Message(content, user, channel, type));
    }

    @Override
    public Optional<Message> readId(UUID id) {
        return messageRepository.readId(id);
    }

    @Override
    public List<Message> readUser(User user) {
        return messageRepository.readUser(user);
    }

    @Override
    public List<Message> readChannel(Channel channel) {
        return messageRepository.readChannel(channel);
    }

    @Override
    public List<Message> readContent(String content) {
        return messageRepository.readContent(content);
    }

    @Override
    public List<Message> readAll() {
        return messageRepository.readAll();
    }

    @Override
    public boolean update(UUID id, String content) {
        Optional<Message> messageToUpdate = messageRepository.readId(id);
        if (messageToUpdate.isEmpty()) { return false; }
        messageToUpdate.get().update(content);
        messageRepository.save(messageToUpdate.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return messageRepository.delete(id);
    }
}
