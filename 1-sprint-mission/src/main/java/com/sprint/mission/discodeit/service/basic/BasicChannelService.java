package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository; // (선택) 채널 생성 시 사용자 존재 검증용

    public BasicChannelService(ChannelRepository channelRepository,
                               UserRepository userRepository) {
        this.channelRepository = Objects.requireNonNull(channelRepository, "channelRepository");
        this.userRepository   = Objects.requireNonNull(userRepository, "userRepository");
    }

    @Override
    public Channel create(String name, List<User> users, List<Message> messages) {
        requireNonBlank(name, "name");
        final List<User> safeUsers = (users == null) ? List.of() : users;
        final List<Message> safeMsgs = (messages == null) ? List.of() : messages;

        // (선택) 사용자 존재 검증
        for (User u : safeUsers) {
            if (u == null || !userRepository.existsById(u.getId())) {
                throw new NoSuchElementException("User not found in channel create: " + (u == null ? null : u.getId()));
            }
        }

        Channel ch = new Channel(name, users, safeMsgs);
        return channelRepository.save(ch);
    }

    @Override
    public Channel findById(UUID id) {
        Channel c = channelRepository.findById(id);
        if (c == null) throw new NoSuchElementException("Channel not found: " + id);
        return c;
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel update(UUID id, String name, String topic) {
        Channel c = channelRepository.findById(id);
        if (c == null) throw new NoSuchElementException("Channel not found: " + id);

        if (name != null && !name.isBlank()) {
            c.update(name); // 엔티티에 update(name) 존재한다고 가정
        }
        // topic 필드가 엔티티에 없으면 무시. 있다면 c.setTopic(topic) 등 호출

        return channelRepository.save(c);
    }

    @Override
    public boolean delete(UUID id) {
        return channelRepository.deleteById(id);
    }

    // ===== helpers =====
    private void requireNonBlank(String v, String field) {
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}