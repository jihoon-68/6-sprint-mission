package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository,
                               UserRepository userRepository,
                               ChannelRepository channelRepository) {
        this.messageRepository  = Objects.requireNonNull(messageRepository, "messageRepository");
        this.userRepository     = Objects.requireNonNull(userRepository, "userRepository");
        this.channelRepository  = Objects.requireNonNull(channelRepository, "channelRepository");
    }

    @Override
    public Message create(User author, String content, Channel channel) {
        Objects.requireNonNull(author, "author");
        Objects.requireNonNull(channel, "channel");
        requireNonBlank(content, "content");

        // 연관 도메인 존재 검증
        if (!userRepository.existsById(author.getId())) {
            throw new NoSuchElementException("Author not found: " + author.getId());
        }
        if (!channelRepository.existsById(channel.getId())) {
            throw new NoSuchElementException("Channel not found: " + channel.getId());
        }

        // (선택) 채널 멤버십 검증: 작성자가 그 채널의 users에 포함되는지 등
        Channel persistedChannel = channelRepository.findById(channel.getId());
        boolean member = persistedChannel.getUsers().stream()
                .anyMatch(u -> u.getId().equals(author.getId()));
        if (!member) {
            throw new IllegalStateException("Author is not a member of the channel: " + channel.getId());
        }

        // 엔티티 생성자 시그니처에 맞춰 생성하세요.
        // 예) new Message(author, content, channel);
        Message m = new Message(author, content, channel);
        return messageRepository.save(m);
    }

    @Override
    public Message findById(UUID id) {
        Message m = messageRepository.findById(id);
        if (m == null) throw new NoSuchElementException("Message not found: " + id);
        return m;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message update(UUID id, String content) {
        requireNonBlank(content, "content");
        Message m = messageRepository.findById(id);
        if (m == null) throw new NoSuchElementException("Message not found: " + id);

        m.update(content); // 엔티티에 update(content) 있다고 가정
        return messageRepository.save(m);
    }

    @Override
    public boolean delete(UUID id) {
        return messageRepository.deleteById(id);
    }

    // ===== helpers =====
    private void requireNonBlank(String v, String field) {
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}